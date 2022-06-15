import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { Title } from '@angular/platform-browser';
import { settings } from 'src/environments/settings';
import { SysBoticaConstant } from 'src/app/shared/constants/sysBoticaConstant';
import { ValidateService } from 'src/app/shared/service/validate.service';
import { UserSubsidiaryService } from 'src/app/shared/service/api/userSubsidiary.service';
import { Subsidiary } from 'src/app/shared/model/response/subsidiary';
import { SipService } from 'src/app/shared/service/api/sip.service';
import { UserLoinDTORequest } from 'src/app/shared/model/request/userLoinDTORequest';
import { UserResponseDTO } from 'src/app/shared/model/response/userResponseDTO';
import { StorageService } from 'src/app/shared/service/storage.service';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-sing-in',
  templateUrl: './sing-in.component.html',
  styleUrls: ['./sing-in.component.scss']
})
export class SingInComponent implements OnInit {

  public hide: boolean = true;
  public msgError!: string;
  public userResponseDTO!: UserResponseDTO;
  public subsidiaries: Subsidiary[] = [];
  @ViewChild('password') password!: ElementRef;
  public frmSingIn!: FormGroup;
  constructor(
    private _formBuilder: FormBuilder,
    private _router: Router,
    private _activatedRoute: ActivatedRoute,
    private _title: Title,
    private _userSubsidiaryService: UserSubsidiaryService,
    private _sipService: SipService,
    private _storageService: StorageService,
  ) { }

  public validateUserName(event?: any) {
    this.setUserName(event.target.value);
  }

  public setUserName(value: string) {
    this.frmSingIn.controls['username'].setValue(ValidateService.validaSoloLetras(value));
  }

  ngOnInit(): void {
    // [formGroup]="frmSingIn" autocomplete="off"
    this.frmSingIn = this._formBuilder.group({
      username: ['', [Validators.required]],
      password: ['', [Validators.required]],
      idSubsidiary: ['', [Validators.required]],
    });
    this._title.setTitle(settings.appTitle + settings.appVerssion + SysBoticaConstant.TITLE_PAGE_SING_IN);
  }

  onSingIn() {
    if (this.frmSingIn.valid) {
      this.onLogin(this.frmSingIn.value);
    }
  }

  nextPassword(event?: any) {
    if (event.key === 'Enter' || event.keyCode === 'Enter' || event.key === 'Tab' || event.keyCode === 'Tab') {
      this.onFindSubsidiaryByUsername(this.frmSingIn.value.username);
      //this.password.nativeElement.focus();
    }
  }

  public clearUsername() {
    this.msgError = SysBoticaConstant.VC_EMPTY;
    this.frmSingIn.controls['username'].setValue('');
  }

  public onBlur() {
    if (this.frmSingIn.value.username) {
      this.onFindSubsidiaryByUsername(this.frmSingIn.value.username);
    }
  }

  public onFindSubsidiaryByUsername(username: string) {
    console.log(username);
    this._userSubsidiaryService.findSubsidiaryByUserName(username)
      .subscribe(data => {
        if (data) {
          console.log(data);
          this.subsidiaries = data;
          this.password.nativeElement.focus();
        }
      },
        (error: HttpErrorResponse) => {
          if (error.status === 404) {
            this.msgError = SysBoticaConstant.MSG_NOT_FOUND_USER;
          }
        }
      );
  }

  public onLogin(dto: UserLoinDTORequest) {
    this._sipService.login(dto)
      .subscribe(
        (data) => {
          // set storage
          this.setStorage(data);
          const redirectURL = this._activatedRoute.snapshot.queryParamMap.get('redirectURL') || '/intranet/signed-in-redirect';
          // Navigate to the redirect url
          this._router.navigateByUrl(redirectURL);
        },
        (error: HttpErrorResponse) => {
          if (error.status === 500) {
            this.msgError = SysBoticaConstant.MSG_INTERNAL_SERVER_ERROR;
          }
        }
      );
  }

  public getIp() {
    this._userSubsidiaryService.getIp().subscribe(data => {
      console.log(data);
    });
  }

  public getIcon(): string {
    return this.hide ? 'visibility' : 'visibility_off'
  }

  private setStorage(dto: UserResponseDTO) {
    this._storageService.setLocalStorage(JSON.stringify(dto.accsesToken), SysBoticaConstant.STORAGE_ACCESS_TOKEN);
    this._storageService.setLocalStorage(JSON.stringify(dto.name), SysBoticaConstant.STORAGE_USER_NAME);
    this._storageService.setLocalStorage(JSON.stringify(dto.authorities), SysBoticaConstant.STORAGE_AUTHORITIES);
    this._storageService.setLocalStorage(JSON.stringify(true), SysBoticaConstant.STORAGE_IS_LOGGED_IN);
  }
}
