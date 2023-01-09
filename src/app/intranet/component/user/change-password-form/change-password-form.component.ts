import { Component, OnInit, AfterViewInit } from '@angular/core';
import { settings } from 'src/environments/settings';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { StorageService } from 'src/app/shared/service/storage.service';
import { UserService } from 'src/app/shared/service/api/user.service';
import { SysBoticaConstant } from 'src/app/shared/constants/sysBoticaConstant';
import { ValidateService } from 'src/app/shared/service/validate.service';
import { HttpErrorResponse } from '@angular/common/http';
import { AlertService } from 'src/app/shared/service/aler.service';
import Swal from "sweetalert2";
import { Router } from '@angular/router';

@Component({
  selector: 'app-change-password-form',
  templateUrl: './change-password-form.component.html',
  styleUrls: ['./change-password-form.component.scss']
})
export class ChangePasswordFormComponent implements OnInit, AfterViewInit {

  public hideConfirmNewPassword: boolean = true;
  public hideNewPassword: boolean = true;
  public hide: boolean = true;
  public blSave: boolean = true;
  public frmChangePassword!: FormGroup;
  public title!: string;
  constructor(
    protected _formBuilder: FormBuilder,
    protected _storageService: StorageService,
    protected _userService: UserService,
    protected _alertService: AlertService,
    protected _storageServive: StorageService,
    protected router: Router,
  ) { }

  ngOnInit(): void {
    this.title = settings.appTitle + ' ' + settings.appVerssion;

    // [formGroup]="frmChangePassword" autocomplete="off"
    this.frmChangePassword = this._formBuilder.group({
      userName: ['', [Validators.required]],
      password: ['', [Validators.required]],
      newPassword: ['', [Validators.required]],
      confirmNewPassword: ['', [Validators.required, ValidateService.validateRepeatPassword]],
    });
  }

  ngAfterViewInit() {
    this.frmChangePassword.controls['userName'].setValue(this.userName());
  }

  public getIconPassword(): string {
    return this.hide ? 'visibility' : 'visibility_off'
  }

  public getIcon(): string {
    return this.hideConfirmNewPassword ? 'visibility' : 'visibility_off'
  }

  public getIconNewPassword(): string {
    return this.hideNewPassword ? 'visibility' : 'visibility_off'
  }

  public onBlSave(): boolean {
    return this.frmChangePassword.invalid;
  }

  public onSubmit() {
    if (this.frmChangePassword.valid) {
      this._alertService.loadingDialog('Cambiando contraseña...');
      this._userService.changePassword(this.frmChangePassword.value)
        .subscribe(
          (data) => {
            this.singOut();
            Swal.close();
          },
          (error: HttpErrorResponse) => {
            Swal.close();
            console.log(error);
            console.log(error.error.status);
            if (error.status === 400) {
              this.noExistDialog('Contraseña actual incorrecto');
            } if (error.status === 404) {
              this.noExistDialog('Usuario no existe');
            } if (error.status === 500 || error.status === 501) {
              this.errorDialog
            }
          }
        )
    }
  }

  public noExistDialog(msg: string): void {
    this._alertService.questionDialog(msg, msg, true, false, 'Entendido', '', 'assets/icons/alert-frame.svg').then(() => {
      this.CleanForm();
    });
  }

  public errorDialog(): void {
    this._alertService.questionDialog('Ha ocurrido un error. Por favor inténtalo nuevamente', '',
      true, false, 'Entendido', '', 'assets/icons/alert-frame.svg').then(() => {
        this.CleanForm();
      });
  }

  public CleanForm() {
    this.frmChangePassword.reset();
    this.onClearForm();
  }

  public singOut(): void {
    this._storageServive.cleanStorageAll();
    this.router.navigate(['sing-out']);
  }

  public onClearForm() {
    this.frmChangePassword.setValue({
      userName: this.userName(),
      password: '',
      newPassword: '',
      confirmNewPassword: ''
    });
  }

  public userName(): String {
    return JSON.parse(this._storageService.getLocalStorage(SysBoticaConstant.STORAGE_USER));
  }

}
