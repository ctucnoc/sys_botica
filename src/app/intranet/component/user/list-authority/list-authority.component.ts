import { Component, OnInit, ViewChild, AfterViewInit } from '@angular/core';
import { settings } from 'src/environments/settings';
import { AuthorityDTO } from 'src/app/shared/model/response/authorityDTO';
import { MatTableDataSource } from '@angular/material/table';
import { MatSort } from '@angular/material/sort';
import { MatPaginator } from '@angular/material/paginator';
import { AuthorityService } from 'src/app/shared/service/api/authority.service';
import { SysBoticaConstant } from 'src/app/shared/constants/sysBoticaConstant';
import { merge } from 'rxjs';
import { switchMap } from 'rxjs/operators';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { AddAuthorityComponent } from '../add-authority/add-authority.component';
import { AuthorityDTORequest } from 'src/app/shared/model/request/authorityDTORequest';
import { NotificationService } from 'src/app/shared/service/notification.service';
import { AlertService } from 'src/app/shared/service/aler.service';

@Component({
  selector: 'app-list-authority',
  templateUrl: './list-authority.component.html',
  styleUrls: ['./list-authority.component.scss']
})
export class ListAuthorityComponent implements OnInit, AfterViewInit {

  public totalElements!: number;
  public searchKey!: string;
  public title!: string;
  public authority!: AuthorityDTO;
  public authorities: AuthorityDTO[] = [];
  public listData!: MatTableDataSource<any>;
  public displayedColumns: string[] = ['ROL', 'DESCRIPCIÓN', 'SELECCIONE'];
  @ViewChild(MatSort) sort!: MatSort;
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  constructor(
    private _authorityService: AuthorityService,
    private _dialog: MatDialog,
    private _alertService: AlertService
  ) { }

  ngOnInit(): void {
    this.title = settings.appTitle + ' ' + settings.appVerssion;
  }

  ngAfterViewInit(): void {
    this.findByName(SysBoticaConstant.VC_EMPTY, SysBoticaConstant.PAG_NRO_INITIAL, SysBoticaConstant.PAG_SIZE_INITIAL);
    this.sort.sortChange.subscribe(() => this.paginator.pageIndex = 0);
    merge(this.sort.sortChange, this.paginator.page)
      .pipe(
        switchMap(() => {
          return this._authorityService.findByName(
            this.searchKey ? this.searchKey : '',
            this.paginator.pageIndex,
            this.paginator.pageSize
          )
        }),
      )
      .subscribe(data => {
        this.authorities = data.content;
        this.listData = new MatTableDataSource(this.authorities);
        this.totalElements = data.totalElements;
      });
  }

  public onCreater(row?: any) {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.width = "30%";
    dialogConfig.panelClass = "custom-dialog";
    dialogConfig.data = row;
    const dialogRef = this._dialog.open(AddAuthorityComponent, dialogConfig);
    dialogRef.afterClosed().subscribe(rpta => {
      if (rpta.action === SysBoticaConstant.VC_OPERATION_UPDATE) {
        this.onUpdate(rpta.authority, rpta.id);
      } if (rpta.action === SysBoticaConstant.VC_OPERATION_ADD) {
        this.onSave(rpta.authority);
      }
    });
  }

  public onEraser() {
    this.clear();
    this.findByName(SysBoticaConstant.VC_EMPTY, SysBoticaConstant.PAG_NRO_INITIAL, SysBoticaConstant.PAG_SIZE_INITIAL);
  }

  public clear() {
    this.searchKey = '';
  }

  public onSearchAuthority(event?: any) { }

  public onDelete(row: any) {
    this._alertService.questionDialog('¿Seguro que quiere eliminar?', '', true, true, 'Aceptar', 'Cancelar', 'assets/icons/alert-frame.svg').then(() => {
      this.delete(row.id, row.name);
    }, () => {
      console.log('celia :: te amo');
    });
  }

  public findByName(name: string, page: number, size: number) {
    this._authorityService.findByName(name, page, size).subscribe(data => {
      if (data) {
        this.authorities = data.content;
        this.listData = new MatTableDataSource(this.authorities);
        this.totalElements = data.totalElements;
      }
    });

  }

  public onUpdate(row: AuthorityDTORequest, id: number) {
    this._authorityService.update(row, id).subscribe((resp: any) => {
      if (resp.status === 200) {
        const idUser: number = resp.body?.id;
        this.onFindById(idUser);
      }
    });
  }

  public onSave(row: AuthorityDTORequest) {
    this._authorityService.save(row).subscribe((resp: any) => {
      if (resp.status === 200) {
        const idUser: number = resp.body?.id;
        this.onFindById(idUser);
      }
    });
  }

  public clearAuthority(): AuthorityDTO[] {
    return this.authorities = [];
  }

  public onFindById(id: number) {
    this._authorityService.findById(id).subscribe(data => {
      if (data) {
        this.clearAuthority();
        this.authorities.push(data);
        this.listData = new MatTableDataSource(this.authorities);
        this.totalElements = SysBoticaConstant.NRO_ELEMENT_DEFAULT;
      }
    });
  }

  public delete(id: number, name: string) {
    this._authorityService.delete(id).subscribe((resp: any) => {
      if (resp.status === 200) {
        this.clear();
      }
    });
  }

}
