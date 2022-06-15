import { Component, OnInit, ViewChild, AfterViewInit } from '@angular/core';
import { settings } from 'src/environments/settings';
import { SaleService } from 'src/app/shared/service/api/sale.service';
import { MatTableDataSource } from '@angular/material/table';
import { MatSort } from '@angular/material/sort';
import { MatPaginator } from '@angular/material/paginator';
import { SaleParam } from 'src/app/shared/model/request/saleDTORequest';
import { SaleDTO } from 'src/app/shared/model/response/saleDTO';
import { SysBoticaConstant } from 'src/app/shared/constants/sysBoticaConstant';
import { ValidateService } from 'src/app/shared/service/validate.service';
import { merge } from 'rxjs';
import { switchMap, debounceTime } from 'rxjs/operators';
import { FormGroup, FormBuilder } from '@angular/forms';
import { ProofPaymentService } from 'src/app/shared/service/api/proofPayment.service';
import { AlertService } from 'src/app/shared/service/aler.service';
import { ProofPaymentDTO } from 'src/app/shared/model/response/proofPaymentDTO';
import Swal from "sweetalert2";
import { HttpErrorResponse } from '@angular/common/http';
import { CustomerService } from 'src/app/shared/service/api/customer.service';
import { CustomerDTO } from 'src/app/shared/model/response/customerDTO';
import { DtSaleService } from 'src/app/shared/service/api/dtSale.service';
import { DtSaleDTO } from 'src/app/shared/model/response/dtSaleDTO';
import { MatDialogConfig, MatDialog } from '@angular/material/dialog';
import { DtSoldProductComponent } from '../dt-sold-product/dt-sold-product.component';
import { saveAs } from 'file-saver';
import { VaucherService } from 'src/app/shared/service/api/vaucher.service';


@Component({
  selector: 'app-search-sold-product',
  templateUrl: './search-sold-product.component.html',
  styleUrls: ['./search-sold-product.component.scss']
})
export class SearchSoldProductComponent implements OnInit, AfterViewInit {

  public customers: CustomerDTO[] = [];
  public showAdvanceFilters = false;
  public sales: SaleDTO[] = [];
  public saleParam!: SaleParam;
  public title!: string;
  public totalElements!: number;
  public datasource!: MatTableDataSource<any>;
  public displayedColumns: string[] = [
    'CLIENTE',
    'COMPROBANTE DE PAGO',
    'FECHA DE VENTA',
    'SELECCIONE'
  ];
  @ViewChild(MatSort) sort!: MatSort;
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  public frmSearch!: FormGroup;
  public proofPayments: ProofPaymentDTO[] = [];
  constructor(
    protected _saleService: SaleService,
    protected _formBuilder: FormBuilder,
    protected _proofPaymentService: ProofPaymentService,
    protected _alertService: AlertService,
    protected _customerService: CustomerService,
    protected _dtSaleService: DtSaleService,
    protected _dialog: MatDialog,
    protected _vaucherService: VaucherService,
  ) { }

  ngOnInit(): void {
    this.title = settings.appTitle + ' ' + settings.appVerssion;

    // [formGroup]="frmUser" autocomplete="off"
    this.frmSearch = this._formBuilder.group({
      idcustomer: [''],
      customer: [''],
      idproofpayment: [''],
      dateFrom: [''],
      dateTo: [''],
    });

    this.frmSearch.get('customer')?.valueChanges
      .pipe(debounceTime(400))
      .subscribe(response => {
        if (response && response.length) {
          this.onFindByKeyWordCustomer(response);
        } else {
          this.customers = [];
        }
      });
  }

  public clearFrmSearch() {
    this.frmSearch.controls['idcustomer'].setValue('');
    this.frmSearch.controls['customer'].setValue('');
    this.frmSearch.controls['idproofpayment'].setValue('');
    this.frmSearch.controls['dateFrom'].setValue('');
    this.frmSearch.controls['dateTo'].setValue('');
  }

  ngAfterViewInit(): void {
    this.onFindAllProofPayment();
    this.saleParam = this.onInit();
    this.onGetAllParameter(this.saleParam, SysBoticaConstant.PAG_NRO_INITIAL, SysBoticaConstant.PAG_SIZE_INITIAL);
    this.sort.sortChange.subscribe(() => this.paginator.pageIndex = 0);
    merge(this.sort.sortChange, this.paginator.page)
      .pipe(
        switchMap(() => {
          return this._saleService.findByAllParameter(
            this.saleParam,
            this.paginator.pageIndex,
            this.paginator.pageSize
          )
        }),
      )
      .subscribe(data => {
        this.sales = data.content;
        this.datasource = new MatTableDataSource(this.sales);
        this.totalElements = data.totalElements;
      });
  }

  public onFindBySale(idSale: number) {
    this._alertService.loadingDialog('Cargando...');
    this._dtSaleService.findBySale(idSale).subscribe(
      (data: any) => {
        this.onDtSaleProduct(data);
        Swal.close();
      }
    );
  }

  public onPrint(id: any) {
    this._alertService.loadingDialog('Descargando...');
    this._vaucherService.findByRePrintVaucher(id).subscribe(
      (data) => {
        const filename = 'Vaucher' + '.pdf';
        //saveAs(data, filename);
        let thefile = new Blob([data]);
        let url = window.URL.createObjectURL(thefile);
        window.open(url);
        Swal.close();
      }, (error: HttpErrorResponse) => {
        console.log(error.error);
      }
    );
  }

  onDtSaleProduct(data: DtSaleDTO[]) {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.width = "90%";
    dialogConfig.panelClass = "custom-dialog";
    dialogConfig.data = data;
    const dialogRef = this._dialog.open(DtSoldProductComponent, dialogConfig);
    dialogRef.afterClosed().subscribe(rpta => {
      if (rpta) {
        console.log('hola sistemas unsch');
      }
    });
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

  public onFindByKeyWordCustomer(key_word: string) {
    this._customerService.findByKeyWordSQL(key_word).subscribe(
      (data: any) => {
        this.customers = data;
      }
    );
  }

  public onSelect(objCustomer: CustomerDTO) {
    this.frmSearch.controls['customer'].setValue(objCustomer.bussinesName ? objCustomer.bussinesName : objCustomer.firstName + ' ' + objCustomer.lastName);
    this.frmSearch.controls['idcustomer'].setValue(objCustomer.id);
  }

  errorDialog(): void {
    this._alertService.questionDialog('Ha ocurrido un error. Por favor inténtalo nuevamente', '',
      true, false, 'Entendido', '', 'assets/icons/alert-frame.svg').then(() => {
      });
  }

  getBlPristine(): boolean {
    return this.frmSearch.controls['idcustomer'].pristine &&
      this.frmSearch.controls['customer'].pristine &&
      this.frmSearch.controls['idproofpayment'].pristine &&
      this.frmSearch.controls['dateFrom'].pristine &&
      this.frmSearch.controls['dateTo'].pristine;
  }

  getBlContentFrmSearch() {
    return this.frmSearch.controls['idcustomer'].value == '' &&
      this.frmSearch.controls['customer'].value == '' &&
      this.frmSearch.controls['idproofpayment'].value == '' &&
      this.frmSearch.controls['dateFrom'].value == '' &&
      this.frmSearch.controls['dateTo'].value == '';
  }

  public onDisabledDownload(): boolean {
    return this.sales.length <= 0;
  }

  public clearIdCustomer() {
    this.frmSearch.controls['customer'].setValue('');
    this.frmSearch.controls['idcustomer'].setValue('');
  }

  public clear() {
    this.frmSearch.reset();
    this.clearFrmSearch();
    this.sales = [];
    this.saleParam = this.onInit();
    this.onGetAllParameter(this.saleParam, SysBoticaConstant.PAG_NRO_INITIAL, SysBoticaConstant.PAG_SIZE_INITIAL);
    this.showAdvanceFilters = false;
  }

  public showAdvanceOptions(show: boolean) {
    this.showAdvanceFilters = show;
  }

  public onSubmit() {
    if (!this.getBlPristine() && !this.getBlContentFrmSearch()) {
      this.saleParam = this.frmSearch.value;
      this.onGetAllParameter(this.saleParam, SysBoticaConstant.PAG_NRO_INITIAL, SysBoticaConstant.PAG_SIZE_INITIAL);
    } else {
      this.onGetAllParameter(this.onInit(), SysBoticaConstant.PAG_NRO_INITIAL, SysBoticaConstant.PAG_SIZE_INITIAL);
    }
    this.showAdvanceFilters = false;
  }

  public onInit(): SaleParam {
    let obj: any = {
      idcustomer: '',
      idproofpayment: '',
      dateFrom: ValidateService.dateYesterday().toString(),
      dateTo: ValidateService.dateToday().toString()
    };
    this.saleParam = obj;
    return this.saleParam;
  }

  public onGetAllParameter(params: SaleParam, page: number, size: number) {
    this._saleService.findByAllParameter(params, page, size).subscribe(
      (data: any) => {
        this.sales = data.content;
        this.datasource = new MatTableDataSource(this.sales);
        this.datasource.paginator = this.paginator;
        this.totalElements = data.totalElements;
      }
    );
  }

}
