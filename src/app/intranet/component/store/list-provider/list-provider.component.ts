import { Component, OnInit, ViewChild } from '@angular/core';
import { settings } from 'src/environments/settings';
import { ProviderDTO } from 'src/app/shared/model/response/providerDTO';
import { MatTableDataSource } from '@angular/material/table';
import { MatSort } from '@angular/material/sort';
import { MatPaginator } from '@angular/material/paginator';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { ProviderService } from 'src/app/shared/service/api/provider.service';
import { AlertService } from 'src/app/shared/service/aler.service';
import { SysBoticaConstant } from 'src/app/shared/constants/sysBoticaConstant';
import { ProviderDTORequest } from 'src/app/shared/model/request/providerDTORequest';
import { catchError, switchMap, finalize } from 'rxjs/operators';
import { HttpErrorResponse } from '@angular/common/http';
import { of, merge } from 'rxjs';
import { AddProviderComponent } from '../add-provider/add-provider.component';
import Swal from "sweetalert2";


@Component({
  selector: 'app-list-provider',
  templateUrl: './list-provider.component.html',
  styleUrls: ['./list-provider.component.scss']
})
export class ListProviderComponent implements OnInit {

  public totalElements!: number;
  public provider!: ProviderDTO;
  public providers: ProviderDTO[] = [];
  public listData!: MatTableDataSource<any>;
  public displayedColumns: string[] = ['NOMBRE COMERCIAL', 'RAZON SOCIAL', 'RUC', 'DIRECCIÓN', 'REPRESENTANTE', 'SELECCIONE'];
  @ViewChild(MatSort) sort!: MatSort;
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  public searchKey!: string;
  public title!: string;
  constructor(
    private _dialog: MatDialog,
    private _providerService: ProviderService,
    private _alertService: AlertService,
  ) { }

  ngOnInit(): void {
    this.title = settings.appTitle + ' ' + settings.appVerssion;
  }

  ngAfterViewInit(): void {
    this.onFindByDescription(SysBoticaConstant.VC_EMPTY, SysBoticaConstant.PAG_NRO_INITIAL, SysBoticaConstant.PAG_SIZE_INITIAL);
    this.sort.sortChange.subscribe(() => this.paginator.pageIndex = 0);
    merge(this.sort.sortChange, this.paginator.page)
      .pipe(
        switchMap(() => {
          return this._providerService.findByName(
            this.searchKey ? this.searchKey : '',
            this.paginator.pageIndex,
            this.paginator.pageSize
          )
        }),
      )
      .subscribe(data => {
        this.providers = data.content;
        this.listData = new MatTableDataSource(this.providers);
        this.totalElements = data.totalElements;
      });
  }

  public onCreater(row?: ProviderDTO) {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.width = "30%";
    dialogConfig.panelClass = "custom-dialog";
    dialogConfig.data = row;
    const dialogRef = this._dialog.open(AddProviderComponent, dialogConfig);
    dialogRef.afterClosed().subscribe(rpta => {
      console.log(rpta);
      if (rpta.action === SysBoticaConstant.VC_OPERATION_UPDATE) {
        console.log('update provaider -> {} ' + rpta);
        this.onUpdate(rpta.provider, rpta.id);
      } if (rpta.action === SysBoticaConstant.VC_OPERATION_ADD) {
        console.log('save provaider -> {} ' + rpta);
        this.onSave(rpta.provider);
      }
    });
  }

  public onEraser() {
    this.clear();
    this.onFindByDescription(SysBoticaConstant.VC_EMPTY, SysBoticaConstant.PAG_NRO_INITIAL, SysBoticaConstant.PAG_SIZE_INITIAL);
  }

  public onSearchProvider(event?: any) {
    if (event.key === 'Enter' || event.keyCode === 'Enter') {
      this.onFindByDescription(this.searchKey, SysBoticaConstant.PAG_NRO_INITIAL, SysBoticaConstant.PAG_SIZE_INITIAL);
    }
  }

  public clear() {
    this.searchKey = '';
  }

  public onDelete(row: any) {
    this._alertService.questionDialog('¿Seguro que quiere eliminar?', '', true, true, 'Aceptar', 'Cancelar', 'assets/icons/alert-frame.svg').then(() => {
      this.onRemove(row.id);
    }, () => {
    });
  }

  public onFindByDescription(key_Word: string, page: number, size: number) {
    this._alertService.loadingDialog('Proveedores Cargando...');
    this._providerService.findByName(key_Word, page, size)
      .pipe(finalize(() => Swal.close()))
      .subscribe(data => {
        this.providers = data.content;
        this.listData = new MatTableDataSource(this.providers);
        this.totalElements = data.totalElements;
      });
  }

  public onFindById(id: number) {
    this._providerService.findById(id).subscribe(data => {
      this.clearPrividers();
      this.providers.push(data);
      this.listData = new MatTableDataSource(this.providers);
      this.totalElements = SysBoticaConstant.NRO_ELEMENT_DEFAULT;
    });
  }

  public clearPrividers(): ProviderDTO[] {
    return this.providers = [];
  }

  public onUpdate(row: ProviderDTORequest, id: number) {
    this._alertService.loadingDialog('Proveedor Actualizando...');
    this._providerService.update(row, id)
      .pipe(
        catchError((error: HttpErrorResponse) => {
          Swal.close();
          if (error.status === 400) {
            this.errorDialog();
          }
          return of({});
        })
      )
      .subscribe((resp: any) => {
        if (resp.status === 200) {
          Swal.close();
          this.onFindById(id);
        }
      });
  }

  public errorDialog(): void {
    this._alertService.questionDialog('Ha ocurrido un error. Por favor inténtalo nuevamente', '',
      true, false, 'Entendido', '', 'assets/icons/alert-frame.svg').then(() => {
      });
  }

  public onSave(row: ProviderDTORequest) {
    this._alertService.loadingDialog('Proveedor Registrando...');
    this._providerService.save(row)
      .pipe(
        catchError((error: HttpErrorResponse) => {
          Swal.close();
          return of({});
        })
      )
      .subscribe((resp: any) => {
        if (resp.status === 200) {
          Swal.close();
          const idUser: number = resp.body?.id;
          this.onFindById(idUser);
        }
      });
  }

  public onRemove(id: number) {
    this._alertService.loadingDialog('Eliminando...');
    this._providerService.delete(id)
      .pipe(finalize(() => Swal.close()))
      .subscribe(
        (data) => {
          this.onFindByDescription(SysBoticaConstant.VC_EMPTY, SysBoticaConstant.PAG_NRO_INITIAL, SysBoticaConstant.PAG_SIZE_INITIAL);
        }
      );
  }

}
