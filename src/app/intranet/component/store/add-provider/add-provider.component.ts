import { Component, OnInit, Inject, ChangeDetectorRef } from '@angular/core';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ProviderDTO } from 'src/app/shared/model/response/providerDTO';
import { ProviderDTORequest } from 'src/app/shared/model/request/providerDTORequest';
import { SysBoticaConstant } from 'src/app/shared/constants/sysBoticaConstant';
import { ValidateService } from 'src/app/shared/service/validate.service';

@Component({
  selector: 'app-add-provider',
  templateUrl: './add-provider.component.html',
  styleUrls: ['./add-provider.component.scss']
})
export class AddProviderComponent implements OnInit {

  public frmProvider!: FormGroup;
  private action!: string;
  private provider!: ProviderDTORequest;
  private id!: number;
  constructor(
    private _formBuilder: FormBuilder,
    private _dialoRef: MatDialogRef<AddProviderComponent>,
    @Inject(MAT_DIALOG_DATA) public data: ProviderDTO,
    private cdRef: ChangeDetectorRef
  ) { }

  public setBussunesName(even?:any){
    this.frmProvider.controls['bussinesname'].setValue(ValidateService.validaSoloLetrasNumerosCaracteresAceptados(even.target.value));
  }

  public setTradeName(even?:any){
    this.frmProvider.controls['tradename'].setValue(ValidateService.validaSoloLetrasNumerosCaracteresAceptados(even.target.value));
  }

  public setRuc(even?:any){
    this.frmProvider.controls['ruc'].setValue(ValidateService.validaSoloNumeros(even.target.value));
  }

  public setAddress(even?:any){
    this.frmProvider.controls['address'].setValue(ValidateService.validaSoloLetrasNumerosCaracteresAceptados(even.target.value));
  }

  public setCellPhone(even?:any){
    this.frmProvider.controls['cellphone'].setValue(ValidateService.validaSoloNumerosTelefonico(even.target.value));
  }

  public setEmail(even?:any){
    this.frmProvider.controls['email'].setValue(ValidateService.validaSoloLetrasNumerosCaracteresAceptados(even.target.value));
  }

  public setWebPage(even?:any){
    this.frmProvider.controls['webpage'].setValue(ValidateService.validaSoloLetrasNumerosCaracteresAceptados(even.target.value));
  }

  public setRepresentative(even?:any){
    this.frmProvider.controls['representative'].setValue(ValidateService.validaSoloLetrasEspacio(even.target.value));
  }

  public onClose(): void {
    this._dialoRef.close(false);
  }

  public ngAfterViewInit(): void {
    if (this.data) {
      this.frmEditProvider(this.data);
    }
    this.cdRef.detectChanges();
  }

  public frmEditProvider(row: ProviderDTO) {
    this.frmProvider.setValue({
      id: row.id,
      bussinesname: row.bussinesname,
      tradename: row.tradename,
      ruc: row.ruc,
      address: row.address,
      cellphone: row.cellphone,
      email: row.email,
      webpage: row.webpage,
      representative: row.representative,
    });
  }

  ngOnInit(): void {
    // [formGroup]="frmProvider" autocomplete="off"
    this.frmProvider = this._formBuilder.group({
      id: [''],
      bussinesname: ['', [Validators.required]],
      tradename: ['', [Validators.required]],
      ruc: ['', [Validators.required]],
      address: [''],
      cellphone: ['', [Validators.required]],
      email: [''],
      webpage: [''],
      representative: ['', [Validators.required]],
    });
  }

  public onSubmit() {
    if (this.frmProvider.valid) {
      this.action = this.frmProvider.value.id ? SysBoticaConstant.VC_OPERATION_UPDATE : SysBoticaConstant.VC_OPERATION_ADD;
      this.id = this.frmProvider.value.id;
      this.provider = this.frmProvider.value;
      this._dialoRef.close({ id: this.id, provider: this.provider, action: this.action });
    }
  }

}
