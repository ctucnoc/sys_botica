import { Component, OnInit, ViewChild, AfterViewInit } from '@angular/core';
import { settings } from 'src/environments/settings';
import { FormBuilder, Validators } from '@angular/forms';
import { MatTableDataSource } from '@angular/material/table';
import { MatSort } from '@angular/material/sort';
import { MatPaginator } from '@angular/material/paginator';
import { UserDTO } from 'src/app/shared/model/response/userDTO';
import { UserService } from 'src/app/shared/service/api/user.service';
import { debounceTime, catchError, switchMap } from 'rxjs/operators';
import { UserAuthorityService } from 'src/app/shared/service/api/userAuthority.service';
import { UserAuthorityDTO } from 'src/app/shared/model/response/userAuthorityDTO';
import { SysBoticaConstant } from 'src/app/shared/constants/sysBoticaConstant';
import { AuthorityDTO } from 'src/app/shared/model/response/authorityDTO';
import { MatDialogConfig, MatDialog } from '@angular/material/dialog';
import { SearchAuthorityComponent } from '../search-authority/search-authority.component';
import { UserAuthorityDTORequest } from 'src/app/shared/model/request/userAuthorityDTORequest';
import { of, merge } from 'rxjs';
import { AlertService } from 'src/app/shared/service/aler.service';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-list-user-authority',
  templateUrl: './list-user-authority.component.html',
  styleUrls: ['./list-user-authority.component.scss']
})
export class ListUserAuthorityComponent implements OnInit, AfterViewInit {

  public authorities: AuthorityDTO[] = [];
  public userAuthorities: UserAuthorityDTO[] = [];
  public users: UserDTO[] = [];
  public totalElements!: number;
  public listData!: MatTableDataSource<UserAuthorityDTO>;
  public displayedColumns: string[] = ['USUARIO', 'ROL O AUTORIZACIÓN', 'SELECCIONE'];
  @ViewChild(MatSort) sort!: MatSort;
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  public title!: string;
  constructor(
    private _formBuilder: FormBuilder,
    private _userService: UserService,
    private _dialog: MatDialog,
    private _userAuthorityService: UserAuthorityService,
    private _alertService: AlertService
  ) { }

  // [formGroup]="frmUser" autocomplete="off"
  frmUser = this._formBuilder.group({
    id: ['', [Validators.required]],
    username: ['', [Validators.required]],
  });

  ngOnInit(): void {
    this.title = settings.appTitle + ' ' + settings.appVerssion;
    this.frmUser.controls['username'].valueChanges
      .pipe(debounceTime(400))
      .subscribe(response => {
        if (response && response.length) {
          this.onFindAutoCompleteFullName(response);
        } else {
          this.clearUser();
        }
      })
  }

  ngAfterViewInit() {
    this.sort.sortChange.subscribe(() => this.paginator.pageIndex = 0);
    merge(this.sort.sortChange, this.paginator.page)
      .pipe(
        switchMap(() => {
          return this._userAuthorityService.findByIduser(
            this.frmUser.value.id,
            this.paginator.pageIndex,
            this.paginator.pageSize
          )
        }),
      )
      .subscribe(data => {
        this.userAuthorities = data.content;
        this.listData = new MatTableDataSource(this.userAuthorities);
        this.totalElements = data.totalElements;
      });
  }

  public clear() { }

  public clearUserAuthority(): UserAuthorityDTO[] {
    return this.userAuthorities = [];
  }

  public onDisablePlus(): boolean {
    return !this.frmUser.valid;
  }

  public onEraser() {
    this.listData = new MatTableDataSource(this.clearUserAuthority());
    this.totalElements = SysBoticaConstant.NRO_ELEMENT_DEFAULT;
  }

  public onSelect(row: UserDTO) {
    this.setFrmUser(row);
    this.onFindByIduser(row.id, SysBoticaConstant.PAG_NRO_INITIAL, SysBoticaConstant.PAG_SIZE_INITIAL);
  }

  public setFrmUser(row: UserDTO) {
    this.frmUser.setValue({
      id: row.id,
      username: row.fullname
    });
  }

  public onDelete(row: UserAuthorityDTO) {
    this._alertService.questionDialog('¿Seguro que quiere eliminar?', '', true, true, 'Aceptar', 'Cancelar', 'assets/icons/alert-frame.svg').then(() => {
      this.onRemove(row);
    }, () => {
      console.log('celia :: ');
    });
  }

  public onFindAutoCompleteFullName(key_word: string) {
    this._userService.findByAutoCompleteFullName(key_word).subscribe(data => {
      if (data) {
        this.clearUser();
        this.users.push(...data);
      }
    });
  }
  public clearUser(): UserDTO[] {
    return this.users = [];;
  }

  public onFindByIduser(id: any, page: number, size: number) {
    this._userAuthorityService.findByIduser(id, page, size).subscribe(data => {
      if (data) {
        this.userAuthorities = data.content;
        this.listData = new MatTableDataSource(this.userAuthorities);
        this.totalElements = data.totalElements;
      }
    });
  }

  public onCreater() {
    if (this.frmUser.value.id) {
      const dialogConfig = new MatDialogConfig();
      dialogConfig.disableClose = true;
      dialogConfig.autoFocus = true;
      dialogConfig.width = "55%";
      dialogConfig.height = "48%";
      dialogConfig.panelClass = "custom-dialog";
      dialogConfig.data = this.frmUser.value.id;
      const dialogRef = this._dialog.open(SearchAuthorityComponent, dialogConfig);
      dialogRef.afterClosed().subscribe((resp: any) => {
        if (resp.blFlag) {
          this.onSave(resp.userAuthority);
        }
      });
    }
  }

  public onSave(row: UserAuthorityDTORequest) {
    this._userAuthorityService.save(row)
      .pipe(
        catchError((error: HttpErrorResponse) => {
          error.status === 422 ? this.personalityDialog('Alerta', 'Rol ya asinado al usuario') : this.errorDialog();
          return of({});
        })
      )
      .subscribe((resp: any) => {
        if (resp.status === 200) {
          this.onFindByIduser(row.idUser, SysBoticaConstant.PAG_NRO_INITIAL, SysBoticaConstant.PAG_SIZE_INITIAL);
        }
      });
  }

  public onRemove(row: UserAuthorityDTO) {
    this._userAuthorityService.delete(row.id)
      .pipe(
        catchError((error: HttpErrorResponse) => {
          if (error.status === 404) {
            this.personalityDialog('Alerta', 'Registro no encontrado')
          }
          return of({});
        })
      )
      .subscribe((resp: any) => {
        if (resp.status === 200) {
          this.onFindByIduser(row.user?.id, SysBoticaConstant.PAG_NRO_INITIAL, SysBoticaConstant.PAG_SIZE_INITIAL);
        }
      });
  }

  public personalityDialog(title: string, subTitle: string): void {
    this._alertService.questionDialog(title, subTitle, true, false, 'Entendido', '', 'assets/icons/alert-frame.svg').then(() => {
    });
  }

  errorDialog(): void {
    this._alertService.questionDialog('Ha ocurrido un error. Por favor inténtalo nuevamente', '',
      true, false, 'Entendido', '', 'assets/icons/alert-frame.svg').then(() => {
      });
  }

}
