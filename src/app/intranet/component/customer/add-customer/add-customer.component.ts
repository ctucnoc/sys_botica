import { Component, OnInit, Inject, ChangeDetectorRef, AfterViewInit } from '@angular/core';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { CustomerDTO } from 'src/app/shared/model/response/customerDTO';
import { SysBoticaConstant } from 'src/app/shared/constants/sysBoticaConstant';
import { CustomerDTORequest } from 'src/app/shared/model/request/customerDTORequest';
import { TypeDocumentDTO } from 'src/app/shared/model/response/typeDocumentDTO';

@Component({
  selector: 'app-add-customer',
  templateUrl: './add-customer.component.html',
  styleUrls: ['./add-customer.component.scss']
})
export class AddCustomerComponent implements OnInit, AfterViewInit {

  public blPersonNatural: boolean = true;
  public typeDocuments: TypeDocumentDTO[] = [];
  public customer!: CustomerDTORequest;
  public id!: number;
  public action!: string;
  public frmCustomer!: FormGroup;
  constructor(
    private _formBuilder: FormBuilder,
    private _dialoRef: MatDialogRef<AddCustomerComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any, //CustomerDTO,
    private cdRef: ChangeDetectorRef
  ) { }

  ngOnInit(): void {
    // [formGroup]="frmUser" autocomplete="off"
    this.frmCustomer = this._formBuilder.group({
      id: [''],
      numberDocument: ['', [Validators.required]],
      firstName: [''],
      lastName: [''],
      bussinesName: [''],
      email: ['', [Validators.email]],
      direcction: [''],
      idTypeDocument: ['', [Validators.required]],
    });
  }

  public frmCustomerEdit(row: CustomerDTO) {
    this.frmCustomer.setValue({
      id: row.id,
      numberDocument: row.numberDocument,
      firstName: row.firstName,
      lastName: row.lastName,
      bussinesName: row.bussinesName,
      email: row.email,
      direcction: row.direcction,
      idTypeDocument: row.idTypeDocument,
    });
  }

  public onClose(): void {
    this._dialoRef.close(false);
  }

  public ngAfterViewInit(): void {
    this.frmCustomer.controls['idTypeDocument'].setValue(SysBoticaConstant.NRO_ELEMENT_DEFAULT);
    this.typeDocuments = this.data.typeDocuments;
    if (this.data.row) {
      this.frmCustomerEdit(this.data.row);
      this.onSelectValue(this.data.row.idTypeDocument);
    }
    this.cdRef.detectChanges();
  }

  public onSelectValue(event: any) {
    if (event === SysBoticaConstant.NRO_VALUE_RUC) {
      this.blPersonNatural = false;
    } else {
      this.blPersonNatural = true;
    }
  }

  public onSubmit() {
    if (this.frmCustomer.valid) {
      this.action = this.frmCustomer.value.id ? SysBoticaConstant.VC_OPERATION_UPDATE : SysBoticaConstant.VC_OPERATION_ADD;
      this.id = this.frmCustomer.value.id;
      this.customer = this.frmCustomer.value;
      this._dialoRef.close({ id: this.id, customer: this.customer, action: this.action });
    }
  }

}
