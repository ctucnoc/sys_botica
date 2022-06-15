import { Component, OnInit, Inject, ChangeDetectorRef, AfterViewInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, AbstractControl } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ProductDTO } from 'src/app/shared/model/response/productDTO';
import { SysBoticaConstant } from 'src/app/shared/constants/sysBoticaConstant';
import { DtEntryProductDTORequest } from 'src/app/shared/model/request/dtEntryProductDTORequest';

@Component({
  selector: 'app-detail-entry-product',
  templateUrl: './detail-entry-product.component.html',
  styleUrls: ['./detail-entry-product.component.scss']
})
export class DetailEntryProductComponent implements OnInit, AfterViewInit {

  private action!: string;
  public dtEntryProductDTORequest!: DtEntryProductDTORequest;
  public blFlagRequiredLot!: boolean;
  public frmDtEntryProduct!: FormGroup;
  public blFlagRequiredExpiratedate!: boolean;
  constructor(
    private _formBuilder: FormBuilder,
    private _dialoRef: MatDialogRef<DetailEntryProductComponent>,
    @Inject(MAT_DIALOG_DATA) public data: ProductDTO,
    private cdRef: ChangeDetectorRef
  ) { }

  ngOnInit(): void {
    // [formGroup]="frmWharehouse" autocomplete="off"
    this.frmDtEntryProduct = this._formBuilder.group({
      id: [''],
      idProduct: ['', [Validators.required]],
      productName: [{ value: '', disabled: true }],
      purchaseprecio: ['', [Validators.required]],
      amount: ['', [Validators.required]],
      expiratedate: [''],
      lotnumber: ['',],
    });

    this.frmDtEntryProduct.controls['purchaseprecio'].valueChanges
      .subscribe(data => {
        this.dtEntryProductDTORequest.purchaseprecio = data;
      });

    this.frmDtEntryProduct.controls['amount'].valueChanges
      .subscribe(data => {
        this.dtEntryProductDTORequest.amount = data;
      });

    this.frmDtEntryProduct.controls['expiratedate'].valueChanges
      .subscribe(data => {
        this.dtEntryProductDTORequest.expiratedate = data;
      });

    this.frmDtEntryProduct.controls['lotnumber'].valueChanges
      .subscribe(data => {
        this.dtEntryProductDTORequest.lotnumber = data;
      });
  }

  ngAfterViewInit(): void {
    this.getDtEntryProduct(this.data);
    this.blFlagRequiredLot = this.data.isbatch == SysBoticaConstant.VC_REQUIRED ? true : false;
    this.blFlagRequiredExpiratedate = this.data.isexpiratedate == SysBoticaConstant.VC_REQUIRED ? true : false;
    this.frmDtEntryProduct.controls['idProduct'].setValue(this.data.id);
    this.frmDtEntryProduct.controls['productName'].setValue(this.data.name);
    this.cdRef.detectChanges();
  }

  public onClose(): void {
    this._dialoRef.close(false);
  }

  public onSubmit() {
    if (this.frmDtEntryProduct.valid) {
      this.action = SysBoticaConstant.VC_OPERATION_ADD;
      this._dialoRef.close({ dtEntryProduct: this.dtEntryProductDTORequest, action: this.action });
    }
  }

  public getDtEntryProduct(product: ProductDTO): DtEntryProductDTORequest {
    let object = {
      idProduct: product.id,
      productName: product.name,
      purchaseprecio: 0.00,
      amount: 0,
      expiratedate: '',
      lotnumber: '',
    };
    this.dtEntryProductDTORequest = object;
    return this.dtEntryProductDTORequest;
  }

  public validateLotNumberRequired(control: AbstractControl): any {
    let value = control.value;
    if (value) {
      let blFlagRequired = this.data == SysBoticaConstant.VC_REQUIRED ? true : false;
      return blFlagRequired ? null : { notRequired: true }
    } else {
      return null;
    }
  }

}
