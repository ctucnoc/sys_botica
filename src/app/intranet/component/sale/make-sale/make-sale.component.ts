import { Component, OnInit, ViewChild, AfterViewInit } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { MatSort } from '@angular/material/sort';
import { MatPaginator } from '@angular/material/paginator';
import { BarcodeScannerLivestreamComponent } from 'ngx-barcode-scanner';
import { DtTransferService } from 'src/app/shared/service/api/dtTransfer.service';
import { ProductSaleDTO } from 'src/app/shared/model/response/productSaleDTO';
import { HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { debounceTime, catchError } from 'rxjs/operators';
import { CustomerService } from 'src/app/shared/service/api/customer.service';
import { CustomerDTO } from 'src/app/shared/model/response/customerDTO';
import { AlertService } from 'src/app/shared/service/aler.service';
import Swal from "sweetalert2";
import { NotificationService } from 'src/app/shared/service/notification.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { of, forkJoin } from 'rxjs';
import { ProofPaymentService } from 'src/app/shared/service/api/proofPayment.service';
import { ProofPaymentDTO } from 'src/app/shared/model/response/proofPaymentDTO';
import { SysBoticaConstant } from 'src/app/shared/constants/sysBoticaConstant';
import { SaleService } from 'src/app/shared/service/api/sale.service';
import { SaleDTORequest } from 'src/app/shared/model/request/saleDTORequest';
import { DtSaleDTORequest } from 'src/app/shared/model/request/dtSaleDTORequest';
import { MatDialogConfig, MatDialog } from '@angular/material/dialog';
import { TypeDocumentDTO } from 'src/app/shared/model/response/typeDocumentDTO';
import { AddCustomerComponent } from '../../customer/add-customer/add-customer.component';
import { TypeDocumentService } from 'src/app/shared/service/api/typeDocument.service';
import { CustomerDTORequest } from 'src/app/shared/model/request/customerDTORequest';
import { CategoryService } from 'src/app/shared/service/api/category.service';
import { CategoryDTO } from 'src/app/shared/model/response/categoryDTO';
import { ConfirmSaleComponent } from '../confirm-sale/confirm-sale.component';
import { SafeResourceUrl, DomSanitizer } from '@angular/platform-browser';


@Component({
  selector: 'app-make-sale',
  templateUrl: './make-sale.component.html',
  styleUrls: ['./make-sale.component.scss']
})
export class MakeSaleComponent implements OnInit, AfterViewInit {

  public blPayment: boolean = true;
  public ziseInit!: number;
  public typeDocuments: TypeDocumentDTO[] = [];
  public details: DtSaleDTORequest[] = [];
  public sale!: SaleDTORequest;
  public proofPayments: ProofPaymentDTO[] = [];
  public paymentTotal: number = 0;
  public customer!: CustomerDTO;
  public products: ProductSaleDTO[] = [];
  public categories: CategoryDTO[] = [];
  public frmAutoComplete!: FormGroup;
  public frmCustomer!: FormGroup;
  public productSales: ProductSaleDTO[] = [];
  public searchKey!: string;
  public totalElements!: number;
  public dataSource!: MatTableDataSource<any>;
  @ViewChild(BarcodeScannerLivestreamComponent) barcodeScanner!: BarcodeScannerLivestreamComponent;
  public barcodeValue!: any;
  public displayedColumns: string[] = ['PRODUCTO', 'F. VENCIMIENTO', 'CANTIDAD', 'PRECIO', 'SUB TOTAL', 'SELECCIONE'];
  @ViewChild(MatSort) sort!: MatSort;
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  public archivePpf_pixel!: SafeResourceUrl;
  constructor(
    protected _dtTransferService: DtTransferService,
    protected _formBuilder: FormBuilder,
    protected _customerService: CustomerService,
    protected _alertService: AlertService,
    protected _notificationService: NotificationService,
    protected _snackBar: MatSnackBar,
    protected _proofPaymentService: ProofPaymentService,
    protected _saleService: SaleService,
    protected _dialog: MatDialog,
    protected _typeDocumentService: TypeDocumentService,
    protected _categoryService: CategoryService,
    protected _sanitizer: DomSanitizer,
  ) { }

  ngOnInit(): void {
    this.ziseInit = SysBoticaConstant.PAG_SIZE_INITIAL_SALE;

    this.frmAutoComplete = this._formBuilder.group({
      nameProduct: [''],
    });

    this.frmCustomer = this._formBuilder.group({
      idCustomer: ['', [Validators.required]],
      numberDocument: ['', [Validators.required]],
      fullName: [{ value: '', disabled: true }, [Validators.required]],
      payment: [{ value: '', disabled: false }, [Validators.required]],
      turned: [{ value: '', disabled: true }, [Validators.required]],
      paymentTotal: [{ value: '', disabled: true }, [Validators.required]],
      idProofPayment: [{ value: SysBoticaConstant.RESOURCE_DEFAULT_PROOF_PAYMENT, disabled: false }, [Validators.required]]
    });

    this.frmAutoComplete.controls['nameProduct'].valueChanges
      .pipe(debounceTime(400))
      .subscribe(response => {
        if (response && response.length) {
          this.onFindByKeyWord(1, response);
        } else {
          this.clearProduct();
        }
      })
  }

  public onDissabledSale(): boolean {
    let rpt: boolean = true;
    rpt = this.frmCustomer.invalid || this.products.length == 0;
    return rpt;
  }

  public onDelete(row: ProductSaleDTO) {
    this._alertService.questionDialog('¿Seguro que quiere eliminar?', '', true, true, 'Aceptar', 'Cancelar', 'assets/icons/alert-frame.svg').then(() => {
      this.products = this.products.filter(bean => bean.id != row.id);
      this.dataSource = new MatTableDataSource(this.products);
      this.paymentTotal = this.onGetTotalPrice();
    }, () => {
    });
  }

  public onMoreProduct(event: any, id: any) {
    this.products.forEach(bean => {
      if (bean.id === id) {
        bean.amountSale = event.target.value;
      }
    });
    this.paymentTotal = this.onGetTotalPrice();
  }

  public onGetTotalPrice(): number {
    let total: any = this.dataSource.data.map(bean => bean.salePrice * bean.amountSale).reduce((acc, value) => acc + value, 0);
    this.frmCustomer.controls['paymentTotal'].setValue(total);
    return total;
  }

  public onSumit() {
    let objt: any = {
      idCustomer: this.frmCustomer.value.idCustomer,
      idProofpayment: this.frmCustomer.value.idProofPayment,
    };
    this.sale = objt
    this.sale.details = this.toDetails(this.products);
    this.onSaveSale(this.sale);
  }

  public toDetails(dtos: ProductSaleDTO[]): DtSaleDTORequest[] {
    this.clearDetails();
    dtos.forEach(bean => {
      let obj: any = {
        idDttransfer: bean.id,
        saleprice: bean.salePrice,
        saleamount: bean.amountSale,
      };
      this.details.push(obj);
    });
    return this.details;
  }

  public clearDetails() {
    this.details = [];
  }

  public onSetFrCustomer(row: CustomerDTO) {
    this.frmCustomer.controls['idCustomer'].setValue(row.id);
    this.frmCustomer.controls['numberDocument'].setValue(row.numberDocument);
    this.frmCustomer.controls['fullName'].setValue(row.firstName + ' ' + row.lastName);
  }

  public clearCustomer() {
    this.frmCustomer.controls['idCustomer'].setValue('');
    this.frmCustomer.controls['numberDocument'].setValue('');
    this.frmCustomer.controls['fullName'].setValue('');
  }

  public initFrCustomer() {
    this.frmCustomer.controls['idProofPayment'].setValue(SysBoticaConstant.RESOURCE_DEFAULT_PROOF_PAYMENT);
    this.frmCustomer.controls['payment'].setValue('');
    this.frmCustomer.controls['turned'].setValue('');
  }

  public onFindAllProofPayment() {
    this._alertService.loadingDialog('cargando...');
    this._proofPaymentService.findAll().subscribe(
      (data) => {
        this.proofPayments = data;
        Swal.close();
      }, (error: HttpErrorResponse) => {
        Swal.close();
        if (error.status === 500) {
          this.errorDialog();
        }
      }
    );
  }

  public onSaveSale(dto: SaleDTORequest) {
    this._alertService.questionDialog('¿Seguro que quiere registrar una venta?', '', true, true, 'Aceptar', 'Cancelar', 'assets/icons/menu/banner_sesifarma.svg').then(() => {
      this._alertService.loadingDialog(SysBoticaConstant.MESSAGE_LOADING_REGISTER);
      this._saleService.save(dto)
        .subscribe(
          (data) => {
            let thefile = window.URL.createObjectURL(data);
            this.archivePpf_pixel = this._sanitizer.bypassSecurityTrustResourceUrl(thefile);
            Swal.close();
            this.onConfirmSale(this.archivePpf_pixel);
          },
          (error: HttpErrorResponse) => {
            Swal.close();
            if (error.status === 500) {
              this.errorDialog();
            } else if (error.status === 404) {
              this._alertService.questionDialog('Error del Cliente', 'Ya existe un usuario con ese identificador', true, false, 'Entendido', '', 'assets/icons/alert-frame.svg').then(() => {
              });
            }
          }
        );
    }, () => {
    });
  }

  public onBlur() {
    if (this.frmCustomer.controls['numberDocument'].value) {
      this.onFindByCustomer(this.frmCustomer.controls['numberDocument'].value);
    }
  }

  public onFindByCustomer(nro_document: string) {
    this._alertService.loadingDialog('Bucando...');
    this._customerService.findByNroDocument(nro_document)
      .subscribe(
        (data) => {
          this.customer = data;
          this.onSetFrCustomer(this.customer);
          Swal.close();
        }, (error: HttpErrorResponse) => {
          Swal.close();
          error.status === 404 ? this.notFound() : this.errorDialog();
        }
      );
  }

  public TotalPaymentOrde() {
    let payment: any = this.frmCustomer.controls['payment'].value;
    if (payment) {
      this.frmCustomer.controls['turned'].setValue(payment - this.paymentTotal);
    }
  }

  public notFound(): void {
    this._alertService.questionDialog('No existe el Cliente en Sesifarma', 'No Existe el Cliente en nuestra base de datos', true, false, 'Entendido', '', 'assets/icons/alert-frame.svg').then(() => {
    });
  }

  errorDialog(): void {
    this._alertService.questionDialog('Ha ocurrido un error. Por favor inténtalo nuevamente', '',
      true, false, 'Entendido', '', 'assets/icons/alert-frame.svg').then(() => {
      });
  }

  public clearProduct() {
    this.frmAutoComplete.reset();
  }

  public getProductSale(dto: ProductSaleDTO): string {
    return dto.productName + ' | F.V ' + dto.dateExpiration + ' | QT ' + dto.amount + ' | Precio ' + dto.salePrice;
  }

  ngAfterViewInit(): void {
    this.onDataInit();
    this.barcodeScanner.start();
  }

  public onDataInit() {
    this._alertService.loadingDialog('cargando...');
    forkJoin({
      typeDocuments: this._typeDocumentService.findById(),
      proofPayments: this._proofPaymentService.findAll(),
      categories: this._categoryService.findAll(),
    }).subscribe(
      (data: any) => {
        Swal.close();
        this.typeDocuments = data.typeDocuments;
        this.proofPayments = data.proofPayments;
        this.categories = data.categories;
        console.log(this.categories);
      },
      (error: HttpErrorResponse) => {
        Swal.close();
        console.log('error -> {} ' + error.error);
      }
    );
  }

  public onTypeDocuments() {
    this._typeDocumentService.findById().subscribe(
      (data) => {
        this.typeDocuments = data;
      },
      (error: HttpErrorResponse) => { }
    );
  }

  public onSelectProduct(row: ProductSaleDTO) {
    this.frmAutoComplete.controls['nameProduct'].setValue(row.productName);
    row.amountSale = 1;
    this.products.push(row);
    this.dataSource = new MatTableDataSource(this.products);
    this.dataSource.paginator = this.paginator;
    this.paymentTotal = this.onGetTotalPrice();
    this.totalElements = this.products.length;
  }

  public onDisabledFrmSale(): boolean {
    return true;
  }

  public applyFilter(event: any) {

  }

  public clear() {
    this.frmCustomer.reset();
    this.initFrCustomer();
    this.frmAutoComplete.reset();
    this.products = [];
    this.details = [];
    this.productSales = [];
    this.dataSource = new MatTableDataSource(this.products);
  }

  public onValueChanges(result: any) {
    this.barcodeValue = result.codeResult.code;
    console.log('sistemas unsch :: ', this.barcodeScanner);
  }

  public onStarted(started: any) {
    console.log(started);
  }

  public onFindByKeyWord(idsubsidiary: number, keyWord: string) {
    this._dtTransferService.findByWordKey(idsubsidiary, keyWord)
      .subscribe(
        (data) => {
          this.productSales = data;
        },
        (error: HttpErrorResponse) => {
          console.error(error.error);
        }
      );
  }

  public onCreaterCustomer(row?: CustomerDTO) {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.width = "30%";
    dialogConfig.panelClass = "custom-dialog";
    dialogConfig.data = { row: row, typeDocuments: this.typeDocuments };
    const dialogRef = this._dialog.open(AddCustomerComponent, dialogConfig);
    dialogRef.afterClosed().subscribe(rpta => {
      console.log(rpta);
      if (rpta.action === SysBoticaConstant.VC_OPERATION_ADD) {
        this.onSave(rpta.customer);
      }
    });
  }

  public onSave(dto: CustomerDTORequest) {
    this._alertService.loadingDialog('Registrando...');
    this._customerService.save(dto)
      .pipe(
        catchError((error: HttpErrorResponse) => {
          Swal.close();
          error.status === 422 ? this.userExistDialog() : this.errorDialog();
          return of({});
        })
      )
      .subscribe((resp: any) => {
        if (resp.status === 200) {
          const numberDocument: any = dto.numberDocument;
          this._notificationService.success('Se registro correctamente', this._snackBar);
          this.onFindByCustomer(numberDocument);
          Swal.close();
        }
      });
  }

  public userExistDialog(): void {
    this._alertService.questionDialog('Ya existe este nro. de documento', 'Ya existe este nro. de documento', true, false, 'Entendido', '', 'assets/icons/alert-frame.svg').then(() => {
    });
  }

  public onConfirmSale(data: any) {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.width = "100%";
    dialogConfig.height = "75%";
    dialogConfig.panelClass = "custom-dialog";
    dialogConfig.data = data;
    const dialogRef = this._dialog.open(ConfirmSaleComponent, dialogConfig);
    dialogRef.afterClosed().subscribe(rpta => {
      this.clear();
    });
  }

}
