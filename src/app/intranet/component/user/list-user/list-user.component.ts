import { Component, OnInit, ViewChild } from '@angular/core';
import { MatSort } from '@angular/material/sort';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { UserDTO } from 'src/app/shared/model/response/userDTO';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { AddUserComponent } from '../add-user/add-user.component';
import { UserDTORequest } from 'src/app/shared/model/request/userDTORequest';
import { UserService } from 'src/app/shared/service/api/user.service';
import { AlertService } from 'src/app/shared/service/aler.service';
import { catchError, switchMap } from 'rxjs/operators';
import { HttpErrorResponse } from '@angular/common/http';
import { of, merge } from 'rxjs';
import { NotificationService } from 'src/app/shared/service/notification.service';
import { SysBoticaConstant } from 'src/app/shared/constants/sysBoticaConstant';
import { settings } from 'src/environments/settings';

@Component({
  selector: 'app-list-user',
  templateUrl: './list-user.component.html',
  styleUrls: ['./list-user.component.scss']
})
export class ListUserComponent implements OnInit {

  public title!: string;
  public totalElements!: number;
  public user!: UserDTO;
  public users: UserDTO[] = [];
  public listData!: MatTableDataSource<any>;
  public displayedColumns: string[] = ['USUARIO', 'NOMBRE COMPLETO', 'EMAIL', 'ESTADO', 'SELECCIONE'];
  @ViewChild(MatSort) sort!: MatSort;
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  public searchKey!: string;
  constructor(
    private _dialog: MatDialog,
    private _userService: UserService,
    private _alterService: AlertService,
  ) { }

  ngOnInit(): void {
    this.title = settings.appTitle + ' ' + settings.appVerssion;
  }

  ngAfterViewInit(): void {
    this.onFindByFullNameAndUserName(SysBoticaConstant.VC_EMPTY, SysBoticaConstant.PAG_NRO_INITIAL, SysBoticaConstant.PAG_SIZE_INITIAL);
    this.sort.sortChange.subscribe(() => this.paginator.pageIndex = 0);
    merge(this.sort.sortChange, this.paginator.page)
      .pipe(
        switchMap(() => {
          return this._userService.findByFullNameAndUsername(
            this.searchKey ? this.searchKey : '',
            this.paginator.pageIndex,
            this.paginator.pageSize
          )
        }),
      )
      .subscribe(data => {
        this.users = data.content;
        this.listData = new MatTableDataSource(this.users);
        this.totalElements = data.totalElements;
      });
  }

  public onSearchUser(event?: any) {
    if (event.key === 'Enter' || event.keyCode === 'Enter') {
      this.onFindByFullNameAndUserName(this.searchKey, SysBoticaConstant.PAG_NRO_INITIAL, SysBoticaConstant.PAG_SIZE_INITIAL);
    }
  }

  public getColor(state: StatusUser): string {
    switch (state) {
      case StatusUser.HABILITADO:
        return '#008000';
      case StatusUser.DESHABILITADO:
        return '#FF0000';
    }
  }

  onCreater(row?: UserDTO) {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.width = "30%";
    dialogConfig.panelClass = "custom-dialog";
    dialogConfig.data = row;
    const dialogRef = this._dialog.open(AddUserComponent, dialogConfig);
    dialogRef.afterClosed().subscribe(rpta => {
      if (rpta.id) {
        this.onUpdate(rpta.user, rpta.id);
      } if (rpta.id == '') {
        this.onSave(rpta.user);
      }
    });
  }

  public onDelete(row: UserDTO) { }

  public clear() {
    this.searchKey = '';
  }

  public clearUser() {
    this.users = [];
  }

  public onEraser() {
    this.clear();
    this.onFindByFullNameAndUserName(SysBoticaConstant.VC_EMPTY, SysBoticaConstant.PAG_NRO_INITIAL, SysBoticaConstant.PAG_SIZE_INITIAL);
  }

  public onSave(user: UserDTORequest) {
    this._userService.save(user)
      .pipe(
        catchError((error: HttpErrorResponse) => {
          error.status === 422 ? this.userExistDialog() : this.errorDialog();
          return of({});
        })
      )
      .subscribe((resp: any) => {
        if (resp.status === 200) {
          const idUser: number = resp.body?.id;
          this.onFindById(idUser);
        }
      });
  }

  public onUpdate(user: UserDTORequest, id: number) {
    this._userService.update(user, id)
      .pipe(
        catchError((error: HttpErrorResponse) => {
          if (error.status === 400) {
            this.errorDialog();
          }
          return of({});
        })
      )
      .subscribe((resp: any) => {
        if (resp.status === 200) {
          const idUser: number = resp.body?.id;
          this.onFindById(idUser);
        }
      });
  }

  public userExistDialog(): void {
    this._alterService.questionDialog('Alerta', 'Ya existe un usuario con ese identificador', true, false, 'Entendido', '', 'assets/icons/alert-frame.svg').then(() => {
    });
  }

  errorDialog(): void {
    this._alterService.questionDialog('Ha ocurrido un error. Por favor inténtalo nuevamente', '',
      true, false, 'Entendido', '', 'assets/icons/alert-frame.svg').then(() => {
      });
  }

  public onFindById(id: number) {
    this.clearUser();
    this._userService.findById(id).subscribe(data => {
      this.users.push(data);
      this.listData = new MatTableDataSource(this.users);
      this.totalElements = SysBoticaConstant.NRO_ELEMENT_DEFAULT;
    });
  }

  public onFindByFullNameAndUserName(key_word: string, page: number, size: number) {
    this._userService.findByFullNameAndUsername(key_word, page, size).subscribe(data => {
      this.users = data.content;
      this.listData = new MatTableDataSource(this.users);
      this.totalElements = data.totalElements;
    });
  }

}

enum StatusUser {
  HABILITADO = '1',
  DESHABILITADO = '0',
}
