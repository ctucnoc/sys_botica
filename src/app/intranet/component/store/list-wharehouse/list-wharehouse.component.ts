import { Component, OnInit, ViewChild } from '@angular/core';
import { settings } from 'src/environments/settings';
import { MatTableDataSource } from '@angular/material/table';
import { MatSort } from '@angular/material/sort';
import { MatPaginator } from '@angular/material/paginator';
import { WharehouseDTO } from 'src/app/shared/model/response/WharehouseDTO';
import { WharehouseService } from 'src/app/shared/service/api/wharehouse.service';
import { SysBoticaConstant } from 'src/app/shared/constants/sysBoticaConstant';
import { merge } from 'rxjs';
import { switchMap } from 'rxjs/operators';
import { WharehouseDTORequest } from 'src/app/shared/model/request/wharehouseDTORequest';
import { MatDialogConfig, MatDialog } from '@angular/material/dialog';
import { AddWharehouseComponent } from '../add-wharehouse/add-wharehouse.component';
import { AlertService } from 'src/app/shared/service/aler.service';

@Component({
  selector: 'app-list-wharehouse',
  templateUrl: './list-wharehouse.component.html',
  styleUrls: ['./list-wharehouse.component.scss']
})
export class ListWharehouseComponent implements OnInit {

  public wharehouses: WharehouseDTO[] = [];
  public wharehouse!: WharehouseDTO;
  public totalElements!: number;
  public searchKey!: string;
  public title!: string;
  public dataSource!: MatTableDataSource<WharehouseDTO>;
  public displayedColumns: string[] = ['ALMACÉN', 'DESCRIPCIÓN', 'TIPO', 'SELECCIONE'];
  @ViewChild(MatSort) sort!: MatSort;
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  constructor(
    protected _wharehouseService: WharehouseService,
    protected _dialog: MatDialog,
    protected _alertService: AlertService,
  ) { }

  ngOnInit(): void {
    this.title = settings.appTitle + ' ' + settings.appVerssion;
  }

  ngAfterViewInit(): void {
    this.onFindByName(SysBoticaConstant.VC_EMPTY, SysBoticaConstant.PAG_NRO_INITIAL, SysBoticaConstant.PAG_SIZE_INITIAL);
    this.sort.sortChange.subscribe(() => this.paginator.pageIndex = 0);
    merge(this.sort.sortChange, this.paginator.page)
      .pipe(
        switchMap(() => {
          return this._wharehouseService.findByName(
            this.searchKey ? this.searchKey : '',
            this.paginator.pageIndex,
            this.paginator.pageSize
          )
        }),
      )
      .subscribe(data => {
        this.wharehouses = data.content;
        this.dataSource = new MatTableDataSource(this.wharehouses);
        this.totalElements = data.totalElements;
      });
  }

  public onCreater(row?: WharehouseDTO) {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.width = "30%";
    dialogConfig.panelClass = "custom-dialog";
    dialogConfig.data = row;
    const dialogRef = this._dialog.open(AddWharehouseComponent, dialogConfig);
    dialogRef.afterClosed().subscribe(rpta => {
      if (rpta.action === SysBoticaConstant.VC_OPERATION_UPDATE) {
        this.onUpdate(rpta.wharehouse, rpta.id);
      } if (rpta.action === SysBoticaConstant.VC_OPERATION_ADD) {
        this.onSave(rpta.wharehouse);
      }
    });
  }

  public clear() {
    this.searchKey = '';
  }

  public onSearchWahrehouse(event?: any) {
    if (event.key === 'Enter' || event.keyCode === 'Enter') {
      this.onFindByName(this.searchKey, SysBoticaConstant.PAG_NRO_INITIAL, SysBoticaConstant.PAG_SIZE_INITIAL);
    }
  }

  public onEraser() {
    this.clear();
    this.onFindByName(SysBoticaConstant.VC_EMPTY, SysBoticaConstant.PAG_NRO_INITIAL, SysBoticaConstant.PAG_SIZE_INITIAL);
  }

  public onFindByName(name: string, page: number, size: number) {
    this._wharehouseService.findByName(name, page, size).subscribe(
      (data) => {
        this.wharehouses = data.content;
        this.dataSource = new MatTableDataSource(this.wharehouses);
        this.totalElements = data.totalElements;
      }
    );
  }

  public onSave(row: WharehouseDTORequest) {
    this._wharehouseService.save(row).subscribe(
      (rpta: any) => {
        if (rpta.status === 200) {
          const id = rpta.body?.id;
          this.onFindById(id);
        }
      }
    );
  }

  public onUpdate(row: WharehouseDTORequest, id: number) {
    this._wharehouseService.update(row, id).subscribe(
      (resp: any) => {
        if (resp.status === 200) {
          this.onFindById(id);
        }
      }
    );
  }

  public onRemove(id: number) {
    this._wharehouseService.delete(id).subscribe(
      (resp: any) => {
        if (resp.status === 200) {
          this.onEraser();
        }
      }
    );
  }

  public onFindById(id: number) {
    this._wharehouseService.findById(id).subscribe(
      (data) => {
        this.clearWharehouses();
        this.wharehouses.push(data);
        this.dataSource = new MatTableDataSource(this.wharehouses);
        this.totalElements = SysBoticaConstant.NRO_ELEMENT_DEFAULT;
      }
    );
  }

  public clearWharehouses(): WharehouseDTO[] {
    return this.wharehouses = [];
  }

  public onDelete(row: any) {
    this._alertService.questionDialog('¿Seguro que quiere eliminar?', '', true, true, 'Aceptar', 'Cancelar', 'assets/icons/alert-frame.svg').then(() => {
      this.onRemove(row.id);
    }, () => {
    });
  }

}
