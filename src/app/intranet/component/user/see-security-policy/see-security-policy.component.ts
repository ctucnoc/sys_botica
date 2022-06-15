import { Component, OnInit, AfterViewInit } from '@angular/core';
import { SecurityPolicyService } from 'src/app/shared/service/api/securityPolicy.service';
import { SecurityPolicyDTO } from 'src/app/shared/model/response/securityPolicyDTO';
import { FormBuilder, Validators } from '@angular/forms';
import { SysBoticaConstant, ARRAY_DOS_OPCIONES } from 'src/app/shared/constants/sysBoticaConstant';
import { SecurityPolicyDTORequest } from 'src/app/shared/model/request/securityPolicyDTORequest';
import { NotificationService } from 'src/app/shared/service/notification.service';
import { settings } from 'src/environments/settings';

@Component({
  selector: 'app-see-security-policy',
  templateUrl: './see-security-policy.component.html',
  styleUrls: ['./see-security-policy.component.scss']
})
export class SeeSecurityPolicyComponent implements OnInit, AfterViewInit {

  public title!: string;
  public ltsDosOpciones: any[] = [];
  public blSave: boolean = true;
  public securityPolicy!: SecurityPolicyDTO;
  constructor(
    private _securityPolicyService: SecurityPolicyService,
    private _formBuilder: FormBuilder,
  ) { }

  // [formGroup]="frmSecurityPolicy" autocomplete="off"
  frmSecurityPolicy = this._formBuilder.group({
    id: ['', [Validators.required]],
    minpasswordlength: ['', [Validators.required]],
    maxpasswordlength: ['', [Validators.required]],
    maxnumberattempts: ['', [Validators.required]],
    maxidletime: ['', [Validators.required]],
    passwordchangefirstlogin: ['', [Validators.required]],
  });

  ngOnInit(): void {
    this.title = settings.appTitle + ' ' + settings.appVerssion;
    this.disabledFrmSecurityPolicy();
    this.ltsDosOpciones = ARRAY_DOS_OPCIONES;
  }

  ngAfterViewInit(): void {
    this.onFindByCode();
  }

  onFindByCode() {
    this._securityPolicyService.findByCode().subscribe(data => {
      if (data) {
        this.securityPolicy = data;
        this.setFrmSecurityPolicy(this.securityPolicy);
      }
    });
  }

  setFrmSecurityPolicy(row: SecurityPolicyDTO) {
    this.frmSecurityPolicy.setValue({
      id: row.id,
      minpasswordlength: row.minpasswordlength,
      maxpasswordlength: row.maxpasswordlength,
      maxnumberattempts: row.maxnumberattempts,
      maxidletime: row.maxidletime,
      passwordchangefirstlogin: row.passwordchangefirstlogin,
    });
  }

  public disabledFrmSecurityPolicy() {
    this.frmSecurityPolicy.controls['minpasswordlength'].disable();
    this.frmSecurityPolicy.controls['maxpasswordlength'].disable();
    this.frmSecurityPolicy.controls['maxnumberattempts'].disable();
    this.frmSecurityPolicy.controls['maxidletime'].disable();
    this.frmSecurityPolicy.controls['passwordchangefirstlogin'].disable();
  }

  public noTdisabledFrmSecurityPolicy() {
    this.blSave = false;
    this.frmSecurityPolicy.controls['minpasswordlength'].enable();
    this.frmSecurityPolicy.controls['maxpasswordlength'].enable();
    this.frmSecurityPolicy.controls['maxnumberattempts'].enable();
    this.frmSecurityPolicy.controls['maxidletime'].enable();
    this.frmSecurityPolicy.controls['passwordchangefirstlogin'].enable();
  }

  public onSave() {
    let securityPoliTemp: SecurityPolicyDTO = this.frmSecurityPolicy.value;
    if (JSON.stringify(this.securityPolicy) === JSON.stringify(securityPoliTemp)) {
    } else {
      this.blSave = true;
      this.onUpdate(this.frmSecurityPolicy.value, this.frmSecurityPolicy.controls['id'].value);
    }
  }

  public onUpdate(row: SecurityPolicyDTORequest, id: number) {
    this._securityPolicyService.update(row, id).subscribe(data => {
      if (data) {
        this.onFindByCode();
        this.disabledFrmSecurityPolicy();
      }
    });
  }

}
