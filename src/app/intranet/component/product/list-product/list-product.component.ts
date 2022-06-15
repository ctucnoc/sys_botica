import { Component, OnInit, ViewChild, AfterViewInit } from '@angular/core';
import { ProductDTO } from 'src/app/shared/model/response/productDTO';
import { MatTableDataSource } from '@angular/material/table';
import { MatSort } from '@angular/material/sort';
import { MatPaginator } from '@angular/material/paginator';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { ProductService } from 'src/app/shared/service/api/product.service';
import { DialogConfirmationComponent } from 'src/app/shared/component/dialog-confirmation/dialog-confirmation.component';
import { AddProductComponent } from '../add-product/add-product.component';
import { SysBoticaConstant } from 'src/app/shared/constants/sysBoticaConstant';
import { ProductDTORequest } from 'src/app/shared/model/request/productDTORequest';
import { AlertService } from 'src/app/shared/service/aler.service';
import Swal from "sweetalert2";
import { finalize, catchError, switchMap } from 'rxjs/operators';
import { HttpErrorResponse } from '@angular/common/http';
import { of, merge } from 'rxjs';
import { settings } from 'src/environments/settings';
import { LoadingService } from 'src/app/shared/service/loading.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { NotificationService } from 'src/app/shared/service/notification.service';

@Component({
  selector: 'app-list-product',
  templateUrl: './list-product.component.html',
  styleUrls: ['./list-product.component.scss']
})
export class ListProductComponent implements OnInit, AfterViewInit {

  public title!: string;
  public products: ProductDTO[] = [];
  public totalElements!: number;
  public listData!: MatTableDataSource<any>;
  public displayedColumns: string[] = [
    'NOMBRE',
    'NOMBRE CORTO',
    'CATEGORIA',
    'GENERICO',
    'CONTROL DE LOTE',
    'FECHA VENCIMIENTO',
    'KIT',
    'SELECCIONE'
  ];
  @ViewChild(MatSort) sort!: MatSort;
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  public searchKey!: string;
  constructor(
    private _dialog: MatDialog,
    private _productService: ProductService,
    private _alertService: AlertService,
    private _loadingService: LoadingService,
    private _snackBar: MatSnackBar,
    private _notificationService: NotificationService,
  ) { }

  ngOnInit(): void {
    this.title = settings.appTitle + ' ' + settings.appVerssion;
  }

  ngAfterViewInit(): void {
    this.onFindByKeyWord(SysBoticaConstant.VC_EMPTY, SysBoticaConstant.PAG_NRO_INITIAL, SysBoticaConstant.PAG_SIZE_INITIAL);
    this.sort.sortChange.subscribe(() => this.paginator.pageIndex = 0);
    merge(this.sort.sortChange, this.paginator.page)
      .pipe(
        switchMap(() => {
          return this._productService.findByKeyWord(
            this.searchKey ? this.searchKey : '',
            this.paginator.pageIndex,
            this.paginator.pageSize
          )
        }),
      )
      .subscribe(data => {
        this.products = data.content;
        this.listData = new MatTableDataSource(this.products);
        this.totalElements = data.totalElements;
      });
  }

  onSearchUnit(event?: any) {
    if (event.key === 'Enter' || event.keyCode === 'Enter') {
      this.onFindByKeyWord(this.searchKey, SysBoticaConstant.PAG_NRO_INITIAL, SysBoticaConstant.PAG_SIZE_INITIAL);
    }
  }

  clear() {
    this.searchKey = '';
  }

  onCreater(row?: any) {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.width = "70%";
    dialogConfig.panelClass = "custom-dialog";
    dialogConfig.data = row;
    const dialogRef = this._dialog.open(AddProductComponent, dialogConfig);
    dialogRef.afterClosed().subscribe(rpta => {
      if (rpta.action === SysBoticaConstant.VC_OPERATION_UPDATE) {
        this.update(rpta.product, rpta.id);
      } if (rpta.action === SysBoticaConstant.VC_OPERATION_ADD) {
        this.onSave(rpta.product);
      }
    });
  }

  onDelete(row: any) {
    this._dialog
      .open(DialogConfirmationComponent, {
        data: `¿Seguro que quiere eliminar?`
      })
      .afterClosed()
      .subscribe((confirmado: Boolean) => {
        if (confirmado) {
          this.delete(row.id);
        }
      });
  }

  onConfirmation() {
    this._dialog
      .open(DialogConfirmationComponent, {
        data: `¿Ya Existe un Producto con ese nombre?`
      })
      .afterClosed()
      .subscribe((confirmado: Boolean) => {
      });
  }

  public onSave(row: any) {
    this._alertService.loadingDialog('Registrando...');
    this._productService.save(row)
      .pipe(finalize(() => Swal.close()))
      .subscribe(
        (data: any) => {
          this.onFindById(data.id);
          this._notificationService.success(SysBoticaConstant.MESSAGE_OBJECT_ADD,this._snackBar);
        },
        (error: HttpErrorResponse) => {
          error.status === 422 ? this.productExistDialog() : this.errorDialog();
        }
      );
  }

  public onFindByKeyWord(keyWord: string, page: number, size: number) {
    this._alertService.loadingDialog('Buscando...');
    this._productService.findByKeyWord(keyWord, page, size)
      .subscribe(
        (data) => {
          this.products = data.content;
          this.listData = new MatTableDataSource(this.products);
          this.totalElements = data.totalElements;
          Swal.close();
        },
        (error: HttpErrorResponse) => {
          this.errorDialog();
          Swal.close();
        });
  }

  public onFindById(id: number) {
    this.clearProducts();
    this._productService.findById(id).subscribe(data => {
      this.products.push(data);
      this.listData = new MatTableDataSource(this.products);
      this.totalElements = SysBoticaConstant.NRO_ELEMENT_DEFAULT;
    });
  }

  public clearProducts(): void {
    this.products = [];
  }

  public productExistDialog(): void {
    this._alertService.questionDialog('Ya existe un producto con ese nombre', 'Ya existe un producto con ese nombre', true, false, 'Entendido', '', 'assets/icons/alert-frame.svg').then(() => {
    });
  }

  errorDialog(): void {
    this._alertService.questionDialog('Ha ocurrido un error. Por favor inténtalo nuevamente', '',
      true, false, 'Entendido', '', 'assets/icons/alert-frame.svg').then(() => {
      });
  }

  public onEraser() {
    this.clearProducts();
    this.clear();
    this.onFindByKeyWord(SysBoticaConstant.VC_EMPTY, SysBoticaConstant.PAG_NRO_INITIAL, SysBoticaConstant.PAG_SIZE_INITIAL);
  }

  public delete(id: number) {
    this._alertService.loadingDialog('Eliminando...');
    this._productService.delete(id)
      .pipe(finalize(() => Swal.close()))
      .subscribe(data => {
        this.products = [];
        this.listData = new MatTableDataSource(this.products);
        this.totalElements = SysBoticaConstant.NRO_ELEMENT_DEFAULT - 1;
      });
  }

  public update(product: ProductDTORequest, id: number) {
    this._alertService.loadingDialog('Actualizando...');
    this._productService.update(product, id)
      .pipe(finalize(() => Swal.close()))
      .subscribe(data => {
        this.products = data;
        this.listData = new MatTableDataSource(this.products);
        this.totalElements = SysBoticaConstant.NRO_ELEMENT_DEFAULT;
      });
  }

}
