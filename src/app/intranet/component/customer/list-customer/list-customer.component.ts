import { Component, OnInit, ViewChild } from '@angular/core';
import { settings } from 'src/environments/settings';
import { MatTableDataSource } from '@angular/material/table';
import { MatSort } from '@angular/material/sort';
import { MatPaginator } from '@angular/material/paginator';
import { CustomerDTO } from 'src/app/shared/model/response/customerDTO';
import { AlertService } from 'src/app/shared/service/aler.service';
import { CustomerService } from 'src/app/shared/service/api/customer.service';
import Swal from "sweetalert2";
import { HttpErrorResponse } from '@angular/common/http';
import { SysBoticaConstant } from 'src/app/shared/constants/sysBoticaConstant';
import { merge, of } from 'rxjs';
import { switchMap, finalize, catchError } from 'rxjs/operators';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { AddCustomerComponent } from '../add-customer/add-customer.component';
import { CustomerDTORequest } from 'src/app/shared/model/request/customerDTORequest';
import { TypeDocumentService } from 'src/app/shared/service/api/typeDocument.service';
import { TypeDocumentDTO } from 'src/app/shared/model/response/typeDocumentDTO';
import { NotificationService } from 'src/app/shared/service/notification.service';
import { MatSnackBar } from '@angular/material/snack-bar';


@Component({
  selector: 'app-list-customer',
  templateUrl: './list-customer.component.html',
  styleUrls: ['./list-customer.component.scss']
})
export class ListCustomerComponent implements OnInit {

  public typeDocuments: TypeDocumentDTO[] = [];
  public customers: CustomerDTO[] = [];
  public totalElements!: number;
  public searchKey!: string;
  public title!: string;
  public datasource!: MatTableDataSource<any>;
  public displayedColumns: string[] = [
    'CLIENTE',
    'EMAIL',
    'NRO. DOCUMENTO',
    'SELECCIONE'
  ];
  @ViewChild(MatSort) sort!: MatSort;
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  constructor(
    protected _alertService: AlertService,
    protected _customerService: CustomerService,
    protected _dialog: MatDialog,
    protected _typeDocumentService: TypeDocumentService,
    protected _notificationService: NotificationService,
    protected _snackBar: MatSnackBar,
  ) { }

  ngOnInit(): void {
    this.title = settings.appTitle + ' ' + settings.appVerssion;
  }

  ngAfterViewInit(): void {
    this.onTypeDocuments();
    this.onFindByKeyWord(SysBoticaConstant.VC_EMPTY, SysBoticaConstant.PAG_NRO_INITIAL, SysBoticaConstant.PAG_SIZE_INITIAL);
    this.sort.sortChange.subscribe(() => this.paginator.pageIndex = 0);
    merge(this.sort.sortChange, this.paginator.page)
      .pipe(
        switchMap(() => {
          return this._customerService.findByKeyWord(
            this.searchKey ? this.searchKey : '',
            this.paginator.pageIndex,
            this.paginator.pageSize
          )
        }),
      )
      .subscribe(data => {
        this.customers = data.content;
        this.datasource = new MatTableDataSource(this.customers);
        this.totalElements = data.totalElements;
      });
  }

  public onSearchCustomer(event: any) {
    if (event.key === 'Enter' || event.keyCode === 'Enter') {
      this.onFindByKeyWord(this.searchKey, SysBoticaConstant.PAG_NRO_INITIAL, SysBoticaConstant.PAG_SIZE_INITIAL);
    }
  }

  public clear() {
    this.searchKey = '';
  }

  public onEraser() {
    this.clear();
    this.clearCustomers();
    this.onFindByKeyWord(SysBoticaConstant.VC_EMPTY, SysBoticaConstant.PAG_NRO_INITIAL, SysBoticaConstant.PAG_SIZE_INITIAL);
  }

  public onTypeDocuments() {
    this._typeDocumentService.findById().subscribe(
      (data) => {
        this.typeDocuments = data;
      },
      (error: HttpErrorResponse) => { }
    );
  }

  public onCreater(row?: CustomerDTO) {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.width = "30%";
    dialogConfig.panelClass = "custom-dialog";
    dialogConfig.data = { row: row, typeDocuments: this.typeDocuments };
    const dialogRef = this._dialog.open(AddCustomerComponent, dialogConfig);
    dialogRef.afterClosed().subscribe(rpta => {
      console.log(rpta.id);
      if (rpta.action === SysBoticaConstant.VC_OPERATION_UPDATE) {
        console.log('update -> {} ' + rpta);
        this.onUpdate(rpta.customer, rpta.id);
      } if (rpta.action === SysBoticaConstant.VC_OPERATION_ADD) {
        console.log('save -> {} ' + rpta);
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
          const idCustomer: number = resp.body?.id;
          this._notificationService.success('Se registro correctamente', this._snackBar);
          this.onFindById(idCustomer);
          Swal.close();
        }
      });
  }

  public onUpdate(dto: CustomerDTORequest, id: number) {
    this._alertService.loadingDialog('Actualizando...');
    this._customerService.update(dto, id)
      .pipe(
        catchError((error: HttpErrorResponse) => {
          Swal.close();
          this.errorDialog();
          return of({});
        })
      )
      .subscribe((resp: any) => {
        if (resp.status === 200) {
          Swal.close();
          const idCustomer: number = resp.body?.id;
          this._notificationService.success('Se actualizo correctamente', this._snackBar);
          console.log('response -> {} ' + id);
          this.onFindById(id);
        }
      });
  }

  public onDeleteCustomer(id: number) {
    this._alertService.loadingDialog('Eliminando...');
    this._customerService.delete(id)
      .pipe(
        catchError((error: HttpErrorResponse) => {
          Swal.close();
          this.errorDialog();
          return of({});
        })
      )
      .subscribe((resp: any) => {
        if (resp.status === 200) {
          this.onEraser();
          this._notificationService.success('Se elimino correctamente', this._snackBar);
          Swal.close();
        }
      });
  }

  public onFindById(id: number) {
    this._customerService.findById(id).subscribe(
      (data) => {
        this.clearCustomers();
        this.customers.push(data);
        this.datasource = new MatTableDataSource(this.customers);
      },
      (error: HttpErrorResponse) => {

      }
    );
  }

  public clearCustomers() {
    this.customers = [];
  }

  public onDelete(row: any) {
    this._alertService.questionDialog('¿Seguro que quiere eliminar?', '', true, true, 'Aceptar', 'Cancelar', 'assets/icons/alert-frame.svg').then(() => {
      this.onDeleteCustomer(row.id);
    }, () => {
    });
  }

  public onFindByKeyWord(keyWord: string, page: number, size: number) {
    this._alertService.loadingDialog('Buscando...');
    this._customerService.findByKeyWord(keyWord, page, size)
      .subscribe(
        (data) => {
          this.customers = data.content;
          this.datasource = new MatTableDataSource(this.customers);
          this.totalElements = data.totalElements;
          Swal.close();
        },
        (error: HttpErrorResponse) => {
          this.errorDialog();
          Swal.close();
        });
  }

  public userExistDialog(): void {
    this._alertService.questionDialog('Ya existe este nro. de documento', 'Ya existe este nro. de documento', true, false, 'Entendido', '', 'assets/icons/alert-frame.svg').then(() => {
    });
  }

  errorDialog(): void {
    this._alertService.questionDialog('Ha ocurrido un error. Por favor inténtalo nuevamente', '',
      true, false, 'Entendido', '', 'assets/icons/alert-frame.svg').then(() => {
      });
  }

}
