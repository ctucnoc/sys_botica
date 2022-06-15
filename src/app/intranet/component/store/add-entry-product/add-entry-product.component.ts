import { Component, OnInit, ViewChild, AfterViewInit, OnDestroy } from '@angular/core';
import { settings } from 'src/environments/settings';
import { MatTableDataSource } from '@angular/material/table';
import { MatSort } from '@angular/material/sort';
import { MatPaginator } from '@angular/material/paginator';
import { WharehouseService } from 'src/app/shared/service/api/wharehouse.service';
import { forkJoin, Subscription, BehaviorSubject, Observable, empty } from 'rxjs';
import { WharehouseDTO } from 'src/app/shared/model/response/WharehouseDTO';
import { SubsidiaryService } from 'src/app/shared/service/api/subsidiary.service';
import { Subsidiary } from 'src/app/shared/model/response/subsidiary';
import { ProductService } from 'src/app/shared/service/api/product.service';
import { ProductDTO } from 'src/app/shared/model/response/productDTO';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { DetailEntryProductComponent } from '../detail-entry-product/detail-entry-product.component';
import { FormBuilder, FormGroup } from '@angular/forms';
import { debounceTime, scan, finalize } from 'rxjs/operators';
import { DtEntryProductDTORequest } from 'src/app/shared/model/request/dtEntryProductDTORequest';
import { SysBoticaConstant } from 'src/app/shared/constants/sysBoticaConstant';
import { EntryDocumentService } from 'src/app/shared/service/api/entryDocument.service';
import { EntryDocumentDTO } from 'src/app/shared/model/response/entryDocumentDTO';
import { ProviderDTO } from 'src/app/shared/model/response/providerDTO';
import { ProviderService } from 'src/app/shared/service/api/provider.service';
import { EntryProductService } from 'src/app/shared/service/api/entryProduct.service';
import { EntryProductDTORequest } from 'src/app/shared/model/request/entryProductDTORequest';
import { AlertService } from 'src/app/shared/service/aler.service';
import Swal from "sweetalert2";
import { NotificationService } from 'src/app/shared/service/notification.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { HttpErrorResponse, HttpStatusCode } from '@angular/common/http';


@Component({
  selector: 'app-add-entry-product',
  templateUrl: './add-entry-product.component.html',
  styleUrls: ['./add-entry-product.component.scss']
})
export class AddEntryProductComponent implements OnInit, AfterViewInit, OnDestroy {

  public entryProduct!: EntryProductDTORequest;
  public totalElementsProvider!: number;
  public entryDocuments: EntryDocumentDTO[] = [];
  public dtsEntryProducts: DtEntryProductDTORequest[] = [];
  public frmAutoComplete!: FormGroup;
  public frmEntryProduct!: FormGroup;
  public products: ProductDTO[] = [];
  public suscriptions: Array<Subscription> = new Array();
  public subsidiaries: Subsidiary[] = [];
  public wharehouses: WharehouseDTO[] = [];
  public providers: ProviderDTO[] = [];
  public totalElements!: number;
  public title!: string;
  public searchKey!: string;
  public dataSource!: MatTableDataSource<DtEntryProductDTORequest>;
  public displayedColumns: string[] = ['PRODUCTO', 'CANTIDAD', 'PRECIO DE COMPRA', 'FECHA VENCIMIENTO', 'SELECCIONE'];
  @ViewChild(MatSort) sort!: MatSort;
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  public options = new BehaviorSubject<ProviderDTO[]>([]);
  public options$!: Observable<ProviderDTO[]>;
  public limit: number = SysBoticaConstant.PAG_SIZE_INITIAL_INFINIT_SCROLBAR;
  public offset: number = SysBoticaConstant.PAG_NRO_INITIAL;
  constructor(
    protected _wharehouseService: WharehouseService,
    protected _subsidiaryService: SubsidiaryService,
    protected _productService: ProductService,
    private _dialog: MatDialog,
    private _formBuilder: FormBuilder,
    protected _entryDocumentService: EntryDocumentService,
    protected _providerService: ProviderService,
    protected _entryProductService: EntryProductService,
    protected _alertService: AlertService,
    protected _notificationService: NotificationService,
    protected _snackBar: MatSnackBar,
  ) {
  }

  ngOnInit(): void {
    this.title = settings.appTitle + ' ' + settings.appVerssion;
    this.frmEntryProduct = this._formBuilder.group({
      idEntryDocument: [''],
      idsubsiadiary: [''],
      idWharehouse: [''],
      idProvider: [''],
    });
    this.frmAutoComplete = this._formBuilder.group({
      nameProduct: [''],
    });

    this.frmAutoComplete.controls['nameProduct'].valueChanges
      .pipe(debounceTime(400))
      .subscribe(response => {
        if (response && response.length) {
          this.filterAutoComplete(response);
        } else {
          this.clearProduct();
        }
      })
  }

  ngAfterViewInit() {
    this.onLoadInit();
  }

  ngOnDestroy(): void {
    this.suscriptions.forEach(suscription => {
      suscription.unsubscribe();
    });
  }

  public selectSubsidiary(id: any) {
    this.onFindAllWharehouseTypeDistribution(id);
  }

  public onLoadInit() {
    this._alertService.loadingDialog('Data Inicial Cargando...');
    forkJoin({
      subsidiaries: this._subsidiaryService.findAll(),
      entryDocuments: this._entryDocumentService.findAll(),
      providers: this._providerService.findAll(SysBoticaConstant.PAG_NRO_INITIAL, SysBoticaConstant.PAG_SIZE_INITIAL_INFINIT_SCROLBAR),
    })
      .pipe(finalize(() => Swal.close()))
      .subscribe(
        (data) => {
          // result get subsidiary
          this.subsidiaries = data.subsidiaries;

          // result get entry documents
          this.entryDocuments = data.entryDocuments;

          // result get provider
          this.providers = data.providers.content;
          this.totalElementsProvider = data.providers.totalElements;
        }
      );
  }

  public onFindAllWharehouseTypeDistribution(id: number) {
    this._wharehouseService.findByAllTypeDistribution(id).subscribe(
      (data) => {
        this.wharehouses = data;
      }
    );
  }

  public onSubmit() {
    if (this.frmEntryProduct.valid && this.dtsEntryProducts.length > 0) {
      this.entryProduct = this.frmEntryProduct.value;
      this.entryProduct.details = this.dtsEntryProducts;
      this.saveEntryProduct(this.entryProduct);
    }
  }

  public onCreater(row: any) { }

  public onDelete(row: DtEntryProductDTORequest) {
    //let index = this.dtsEntryProducts.findIndex(dtEntryProduct => dtEntryProduct.idProduct === row.idProduct);
    //this.dtsEntryProducts = this.dtsEntryProducts.splice(0,index);
    this.dtsEntryProducts = this.dtsEntryProducts.filter(dtEntryProduct => dtEntryProduct.idProduct != row.idProduct);
    this.dataSource = new MatTableDataSource(this.dtsEntryProducts);
  }

  public clear() { }

  public clearProduct() {
    this.frmAutoComplete.controls['nameProduct'].setValue('');
  }

  public filterAutoComplete(value: string) {
    this.suscriptions.push(
      this._productService.filterAutoComplete(value).subscribe(
        (data) => {
          this.products = data;
        }
      )
    );
  }

  public onSearchProduct(event?: any) {
    const key_word = event.target.value;
    if (key_word) {
      this.filterAutoComplete(key_word);
    }
  }

  public onSelectProduct(product: any) {
    this.frmAutoComplete.controls['nameProduct'].setValue(product.name);
    let quantity = this.dtsEntryProducts.filter(bean => bean.idProduct == product.id);
    if (quantity.length == 0) {
      this.onDetailtEntryProduct(product);
      this.clearProduct();
    } else {
      this._notificationService.warn('Producto ya Agregado', this._snackBar);
    }
  }

  public onDetailtEntryProduct(row: ProductDTO) {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.width = "30%";
    dialogConfig.panelClass = "custom-dialog";
    dialogConfig.data = row;
    const dialogRef = this._dialog.open(DetailEntryProductComponent, dialogConfig);
    dialogRef.afterClosed().subscribe(rpta => {
      if (rpta.action == SysBoticaConstant.VC_OPERATION_ADD) {
        this.addList(rpta.dtEntryProduct);
      }
    });
  }

  public addList(objt: DtEntryProductDTORequest) {
    this.dtsEntryProducts.push(objt);
    this.dataSource = new MatTableDataSource(this.dtsEntryProducts);
  }

  public findAllByProvider(page: number, size: number) {
    this._providerService.findAll(page, size).subscribe(
      (data) => {
        this.providers = data.content;
      }
    );
  }

  public saveEntryProduct(dto: EntryProductDTORequest) {
    this._alertService.questionDialog('¿Seguro que quiere registrar una compra?', '', true, true, 'Aceptar', 'Cancelar', 'assets/icons/warehouse.svg').then(() => {
      this._alertService.loadingDialog(SysBoticaConstant.MESSAGE_LOADING_REGISTER);
      this._entryProductService.save(dto)
        .subscribe(
          (resp: any) => {
            if (resp.status === 200) {
              const idUser: number = resp.body?.id;
              this._notificationService.success(SysBoticaConstant.MESSAGE_OBJECT_ADD, this._snackBar);
              Swal.close();
              this.clearFormEntyProduct();
            }
          }, (error: HttpErrorResponse) => {
            Swal.close();
            this.errorHandler(error.message);
          });
    }, () => {
    });
  }

  public errorHandler(errorMessage: string): void {
    this._alertService.questionDialog(errorMessage, '', true,
      false, 'Entendido', '', 'assets/icons/alert-frame.svg').then(() => {
      });
  }

  public entryProductDialog(): void {
    this._alertService.questionDialog('Ingreso de Productos registrado correctamente', 'Ingreso de Productos registrado correctamente', true, false, 'Entendido', '', 'assets/icons/alert-frame.svg').then(() => {
      this.clearFormEntyProduct();
    });
  }

  errorDialog(): void {
    this._alertService.questionDialog('Ha ocurrido un error. Por favor inténtalo nuevamente', '',
      true, false, 'Entendido', '', 'assets/icons/alert-frame.svg').then(() => {
      });
  }

  public clearFormEntyProduct() {
    this.clear();
    this.frmEntryProduct.reset();
    this.dtsEntryProducts = [];
    this.dataSource = new MatTableDataSource(this.dtsEntryProducts);
  }

}
