import { Component, OnInit, AfterViewInit, ViewChild } from '@angular/core';
import { settings } from 'src/environments/settings';
import { SubsidiaryService } from 'src/app/shared/service/api/subsidiary.service';
import { AlertService } from 'src/app/shared/service/aler.service';
import { Subsidiary } from 'src/app/shared/model/response/subsidiary';
import { finalize, switchMap, catchError } from 'rxjs/operators';
import Swal from "sweetalert2";
import { WharehouseDTO } from 'src/app/shared/model/response/WharehouseDTO';
import { WharehouseService } from 'src/app/shared/service/api/wharehouse.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { DtEntryProductService } from 'src/app/shared/service/api/dtEntryProduct.service';
import { MatTableDataSource } from '@angular/material/table';
import { MatSort } from '@angular/material/sort';
import { MatPaginator } from '@angular/material/paginator';
import { DtEntryProductDTO } from 'src/app/shared/model/response/dtEntryProductDTO';
import { SysBoticaConstant } from 'src/app/shared/constants/sysBoticaConstant';
import { pipe, merge, of } from 'rxjs';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { DialogIncomeAmountComponent } from '../dialog-income-amount/dialog-income-amount.component';
import { DtTransferDTORequest } from 'src/app/shared/model/request/dtTransferDTORequest';
import { TransferService } from 'src/app/shared/service/api/transfer.service';
import { TransferDTORequest } from 'src/app/shared/model/request/transferDTORequest';
import { NotificationService } from 'src/app/shared/service/notification.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { HttpErrorResponse } from '@angular/common/http';


@Component({
  selector: 'app-add-out-sub-warehouse',
  templateUrl: './add-out-sub-warehouse.component.html',
  styleUrls: ['./add-out-sub-warehouse.component.scss']
})
export class AddOutSubWarehouseComponent implements OnInit, AfterViewInit {

  public transfer!: TransferDTORequest;
  public nrAmount: number = 0;
  public idwharehouse: number = 0;
  public totalElements!: number;
  public totalElementsNew!: number;
  public dtTransfers: DtTransferDTORequest[] = [];
  public dtEntryProducts: DtEntryProductDTO[] = [];
  public distributions: WharehouseDTO[] = [];
  public subwarehouses: WharehouseDTO[] = [];
  public subsidiaries: Subsidiary[] = [];
  public title!: string;
  public frmMoveWharehouse!: FormGroup;
  public dataSource!: MatTableDataSource<DtEntryProductDTO>;
  public dataSourceNew!: MatTableDataSource<DtTransferDTORequest>;
  public displayedColumns: string[] = ['PRODUCTO', 'F. VENCIMIENTO', 'CANTIDAD', 'SELECCIONE'];
  public displayedColumnsNew: string[] = ['PRODUCTO', 'F. VENCIMIENTO','PRECIO DE VENTA', 'CANTIDAD', 'SELECCIONE'];
  @ViewChild(MatSort) sort!: MatSort;
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  public searchKey!: string;
  constructor(
    protected _subsidiaryService: SubsidiaryService,
    protected _alertService: AlertService,
    protected _wharehouseService: WharehouseService,
    protected _formBuilder: FormBuilder,
    protected _dtEntryProductService: DtEntryProductService,
    protected _dialog: MatDialog,
    protected _transferService: TransferService,
    protected _notificationService: NotificationService,
    protected _snackBar: MatSnackBar,
  ) { }

  ngOnInit(): void {
    this.title = settings.appTitle + ' ' + settings.appVerssion;
    this.frmMoveWharehouse = this._formBuilder.group({
      idwharehouseorigin: ['', [Validators.required]],
      idwharehousedestination: ['', [Validators.required]],
      idsubsidiary: ['', [Validators.required]]
    });
  }

  ngAfterViewInit() {
    this.onFindBySubsidiaryAll();
    this.sort.sortChange.subscribe(() => this.paginator.pageIndex = 0);
    merge(this.sort.sortChange, this.paginator.page)
      .pipe(
        switchMap(() => {
          return this._dtEntryProductService.findByIdwharehouse(
            this.idwharehouse,
            this.searchKey ? this.searchKey : '',
            this.paginator.pageIndex,
            this.paginator.pageSize
          )
        }),
      )
      .subscribe(data => {
        this.dtEntryProducts = data.content;
        this.dataSource = new MatTableDataSource(this.dtEntryProducts);
        this.totalElements = data.totalElements;
      });
  }

  public onSave() {
    if (!this.onDisabledSave()) {
      this.transfer = this.frmMoveWharehouse.value;
      this.transfer.detail = this.dtTransfers;
      this.onSaveTransfer(this.transfer);
    }
  }

  public onDisabledSave(): boolean {
    return this.frmMoveWharehouse.invalid || this.dtTransfers.length == 0;
  }

  public onDisabledSearchProduct(): boolean {
    return this.idwharehouse > 0 ? false : true;
  }

  public onDisabledFrMoveWharehouse(): boolean {
    return this.frmMoveWharehouse.invalid;
  }

  onDialogIncomeAmount(row: DtEntryProductDTO) {
    let quantityTemp: DtEntryProductDTO[] = this.dtTransfers.filter(bean => bean.iddtentryproduct === row.id);
    let amountControl: any = row.amount;
    if (quantityTemp.length > 0) {
      const tempQu: any = quantityTemp[0].amount;
      amountControl = amountControl - tempQu;
    }
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.width = "30%";
    dialogConfig.panelClass = "custom-dialog";
    dialogConfig.data = { row: row, amountControl: amountControl };
    const dialogRef = this._dialog.open(DialogIncomeAmountComponent, dialogConfig);
    dialogRef.afterClosed().subscribe(rpta => {
      if (rpta.action === SysBoticaConstant.VC_OPERATION_ADD) {
        let objTemp: DtTransferDTORequest[] = this.dtTransfers.filter(bean => bean.iddtentryproduct === rpta.dtTransfer.iddtentryproduct);
        if (objTemp.length > 0) {
          this.dtTransfers.map(bean => {
            if (bean.iddtentryproduct === rpta.dtTransfer.iddtentryproduct) {
              let amountOne: any = bean.amount;
              let amountTwo: any = rpta.dtTransfer.amount;
              bean.amount = amountTwo + amountOne;
            }
          });
        } else {
          this.dtTransfers.push(rpta.dtTransfer);
          this.nrAmount = this.sumaAmountProduct(this.nrAmount);
        }
        this.dataSourceNew = new MatTableDataSource(this.dtTransfers);
        this.dataSource.paginator = this .paginator;
        this.totalElementsNew = this.dtTransfers.length;
      }
    });
  }

  public clear() {
    this.searchKey = '';
  }

  public onDelete(row: DtTransferDTORequest) {
    this._alertService.questionDialog('¿Seguro que quiere eliminar?', '', true, true, 'Aceptar', 'Cancelar', 'assets/icons/alert-frame.svg').then(() => {
      this.dtTransfers = this.dtTransfers.filter(bean => bean.iddtentryproduct != row.iddtentryproduct);
      this.dataSourceNew = new MatTableDataSource(this.dtTransfers);
      this.nrAmount = this.nrAmount - 1;
    }, () => {
    });
  }

  public clearTotal() {
    this.clear();
    this.clearFrmMoveWharehouse();
    this.dtEntryProducts = [];
    this.dataSource = new MatTableDataSource(this.dtEntryProducts);
    this.dtTransfers = [];
    this.dataSourceNew = new MatTableDataSource(this.dtTransfers);
    this.nrAmount = 0;
    this.idwharehouse = 0;
  }

  public clearFrmMoveWharehouse() {
    this.frmMoveWharehouse.reset();
    this.frmMoveWharehouse.setValue({
      idwharehouseorigin: '',
      idwharehousedestination: '',
      idsubsidiary: '',
    });
  }

  public sumaAmountProduct(nro: number): number {
    return nro + 1;
  }

  public onFindBySubsidiaryAll() {
    this._alertService.loadingDialog('Cargando...');
    this._subsidiaryService.findAll()
      .pipe(finalize(() => Swal.close()))
      .subscribe(data => {
        this.subsidiaries = data;
      });
  }

  public selectSubsidiary(id: any) {
    this.onFindAllWharehouseTypeDistribution(id);
  }

  public onFindByIdWharehouseSQL(id: any) {
    this.idwharehouse = id;
    this.onFindByIdwharehouse(id, SysBoticaConstant.VC_EMPTY, SysBoticaConstant.PAG_NRO_INITIAL, SysBoticaConstant.PAG_SIZE_INITIAL);
  }

  public onFindAllWharehouseTypeDistribution(id: number) {
    this._wharehouseService.findAllTypeWharehouse(id).subscribe(
      (data) => {
        console.log(data);
        this.distributions = data.distributions;
        this.subwarehouses = data.subwarehouses;
      }
    );
  }

  public onFindByIdwharehouse(idwharehouse: number, word_key: string, page: number, size: number) {
    this._alertService.loadingDialog('Cargando...');
    this._dtEntryProductService.findByIdwharehouse(idwharehouse, word_key, page, size)
      .pipe(finalize(() => Swal.close()))
      .subscribe(
        (data) => {
          this.dtEntryProducts = data.content;
          this.dataSource = new MatTableDataSource(this.dtEntryProducts);
          this.totalElements = data.totalElements;
        });
  }

  public applyFilter(event?: any) {
    if (event.key === 'Enter' || event.keyCode === 'Enter') {
      this.onFindByIdwharehouse(this.idwharehouse, this.searchKey, SysBoticaConstant.PAG_NRO_INITIAL, SysBoticaConstant.PAG_SIZE_INITIAL);
    }
  }

  public onSaveTransfer(dto: TransferDTORequest) {
    this._alertService.loadingDialog('Registrando...');
    this._transferService.save(dto)
      .pipe(
        finalize(() => Swal.close()),
        catchError((error: HttpErrorResponse) => {
          this.errorDialog();
          return of({});
        })
      )
      .subscribe((resp: any) => {
        if (resp.status === 200) {
          const idUser: number = resp.body?.id;
          this.clearTotal();
          this._notificationService.success('Se registro una nueva transferencia con identificador ' + idUser, this._snackBar);
        }
      });
  }

  errorDialog(): void {
    this._alertService.questionDialog('Ha ocurrido un error. Por favor inténtalo nuevamente', '',
      true, false, 'Entendido', '', 'assets/icons/alert-frame.svg').then(() => {
      });
  }

}
