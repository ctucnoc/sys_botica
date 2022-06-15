import { Component, OnInit, ViewChild } from '@angular/core';
import { UserSubsidiaryDTO } from 'src/app/shared/model/response/userSubsidiaryDTO';
import { MatTableDataSource } from '@angular/material/table';
import { MatSort } from '@angular/material/sort';
import { MatPaginator } from '@angular/material/paginator';
import { UserSubsidiaryService } from 'src/app/shared/service/api/userSubsidiary.service';
import { SysBoticaConstant } from 'src/app/shared/constants/sysBoticaConstant';
import { UserService } from 'src/app/shared/service/api/user.service';
import { UserDTO } from 'src/app/shared/model/response/userDTO';
import { FormBuilder, Validators } from '@angular/forms';
import { debounceTime, catchError } from 'rxjs/operators';
import { MatDialogConfig, MatDialog } from '@angular/material/dialog';
import { SearchSubsidiaryComponent } from '../search-subsidiary/search-subsidiary.component';
import { UserSubsidiaryDTORequest } from 'src/app/shared/model/request/userSubsidiaryDTORequest';
import { HttpErrorResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { AlertService } from 'src/app/shared/service/aler.service';
import { settings } from 'src/environments/settings';

@Component({
  selector: 'app-list-user-subsidiary',
  templateUrl: './list-user-subsidiary.component.html',
  styleUrls: ['./list-user-subsidiary.component.scss']
})
export class ListUserSubsidiaryComponent implements OnInit {

  public title!: string;
  public users: UserDTO[] = [];
  public totalElements!: number;
  public userSubsidiary!: UserSubsidiaryDTO;
  public userSubsidiaries: UserSubsidiaryDTO[] = [];
  public listData!: MatTableDataSource<any>;
  public displayedColumns: string[] = ['USUARIO', 'SUCURSAL','SELECCIONE'];
  @ViewChild(MatSort) sort!: MatSort;
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  public searchKey!: string;
  constructor(
    private _userSubsidiaryService: UserSubsidiaryService,
    private _userService: UserService,
    private _formBuilder: FormBuilder,
    private _dialog: MatDialog,
    private _alertService: AlertService,
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

  public setFrmUser(row: UserDTO) {
    this.frmUser.setValue({
      id: row.id,
      username: row.fullname
    });
  }

  public onCreater() {
    if (this.frmUser.valid) {
      const dialogConfig = new MatDialogConfig();
      dialogConfig.disableClose = true;
      dialogConfig.autoFocus = true;
      dialogConfig.width = "55%";
      dialogConfig.height = "52%";
      dialogConfig.panelClass = "custom-dialog";
      dialogConfig.data = this.frmUser.value;
      const dialogRef = this._dialog.open(SearchSubsidiaryComponent, dialogConfig);
      dialogRef.afterClosed().subscribe(rpta => {
        if (rpta.blFlag) {
          this.onSave(rpta.userSubsidiary);
        }
      });
    }
  }
  public onEraser() {
    this.clear();
    this.clearUserSubsidiaries();
    this.listData = new MatTableDataSource(this.userSubsidiaries);
  }
  public clear() {
    this.clearUser();
  }

  public clearUserSubsidiaries() {
    return this.userSubsidiaries = [];
  }

  public onDisablePlus(): boolean {
    return !this.frmUser.valid;
  }

  public onSelect(row: UserDTO) {
    this.setFrmUser(row);
    this.onFindByUserAllSubsidiary(row.id);
  }

  public onFindByUserAllSubsidiary(id: any) {
    this._userSubsidiaryService.findByUserAllSubsidiary(id, SysBoticaConstant.PAG_NRO_INITIAL, SysBoticaConstant.PAG_SIZE_INITIAL).subscribe(data => {
      if (data) {
        this.userSubsidiaries = data.content;
        this.listData = new MatTableDataSource(this.userSubsidiaries);
        this.totalElements = data.totalElements;
      }
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

  public onSave(row: UserSubsidiaryDTORequest) {
    this._userSubsidiaryService.save(row)
      .pipe(
        catchError((error: HttpErrorResponse) => {
          if (error.status === 422) {
            this.userExistDialog();
          } if (error.status === 400) {
            this.errorDialog();
          }
          return of({});
        })
      )
      .subscribe((resp: any) => {
        if (resp.status === 200) {
          this.onFindByUserAllSubsidiary(row.idUser);
        }
      });
  }

  onDelete(id:number){
    this._alertService.questionDialog('¿Seguro que quiere eliminar?', '', true, true, 'Aceptar', 'Cancelar', 'assets/icons/alert-frame.svg').then(() => {
      this.delete(id);
    },()=>{
      console.log('celia :: ');
    });
  }

  public delete(id: number) {
    this._userSubsidiaryService.delete(id).subscribe(data => {
      if (data) {
        this.onFindByUserAllSubsidiary(this.frmUser.controls['id'].value);
      }
    });
  }

  public clearUser(): UserDTO[] {
    return this.users = [];;
  }

  public userExistDialog(): void {
    this._alertService.questionDialog('Sucursal ya Asignado', '', true, false, 'Entendido', '', 'assets/icons/alert-frame.svg').then(() => {
    });
  }

  errorDialog(): void {
    this._alertService.questionDialog('Ha ocurrido un error. Por favor inténtalo nuevamente', '',
      true, false, 'Entendido', '', 'assets/icons/alert-frame.svg').then(() => {
      });
  }

}
