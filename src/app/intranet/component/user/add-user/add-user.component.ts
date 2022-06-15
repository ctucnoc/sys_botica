import { Component, OnInit, Inject, AfterViewInit, ChangeDetectorRef } from '@angular/core';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';
import { UserDTORequest } from 'src/app/shared/model/request/userDTORequest';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { UserDTO } from 'src/app/shared/model/response/userDTO';
import { SysBoticaConstant } from 'src/app/shared/constants/sysBoticaConstant';
import { ValidateService } from 'src/app/shared/service/validate.service';

@Component({
  selector: 'app-add-user',
  templateUrl: './add-user.component.html',
  styleUrls: ['./add-user.component.scss']
})

export class AddUserComponent implements OnInit, AfterViewInit {

  public frmUser!: FormGroup;
  private action!: string;
  public blReadonly!: boolean;
  public states: any = STATEUSER;
  private user!: UserDTORequest;
  private id!: number;
  constructor(
    private _formBuilder: FormBuilder,
    private _dialoRef: MatDialogRef<AddUserComponent>,
    @Inject(MAT_DIALOG_DATA) public data: UserDTO,
    private cdRef: ChangeDetectorRef
  ) { }

  public onClose(): void {
    this._dialoRef.close(false);
  }

  public ngAfterViewInit(): void {
    this.blReadonly = this.data ? true : false;
    if (this.data) {
      this.frmEditUser(this.data);
    }
    this.cdRef.detectChanges();
  }

  ngOnInit(): void {
    // [formGroup]="frmUser" autocomplete="off"
    this.frmUser = this._formBuilder.group({
      id: [''],
      username: ['', [Validators.required]],
      email: ['', [Validators.required]],
      fullname: ['', [Validators.required]],
      state: ['', [Validators.required]],
    });
  }
  public validateUserName(event?: any) {
    this.setUserName(event.target.value);
  }

  public validateEmail(event?: any) {
    this.setUserEmail(event.target.value);
  }

  public validateFullName(event?: any) {
    this.setUserFullName(event.target.value);
  }

  public setUserName(value: string) {
    this.frmUser.controls['username'].setValue(ValidateService.validaSoloLetras(value));
  }

  public setUserEmail(value: string) {
    this.frmUser.controls['email'].setValue(ValidateService.validaSoloLetrasNumerosCaracteresAceptados(value));
  }

  public setUserFullName(value: string) {
    this.frmUser.controls['fullname'].setValue(ValidateService.validaSoloLetrasEspacio(value));
  }

  public frmEditUser(row: UserDTO) {
    this.frmUser.setValue({
      id: row.id,
      username: row.username,
      email: row.email,
      fullname: row.fullname,
      state: row.state
    });
  }

  public onSubmit() {
    if (this.frmUser.valid) {
      this.action = this.frmUser.value.id ? SysBoticaConstant.VC_OPERATION_UPDATE : SysBoticaConstant.VC_OPERATION_ADD;
      this.id = this.frmUser.value.id;
      this.user = this.frmUser.value;
      this._dialoRef.close({ id: this.id, user: this.user });
    }
  }

}

export const STATEUSER = [
  {
    id: '1',
    description: 'HABILITADO'
  },
  {
    id: '0',
    description: 'DESHABILITADO'
  }
];
