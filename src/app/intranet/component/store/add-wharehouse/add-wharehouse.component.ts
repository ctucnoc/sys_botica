import { Component, OnInit, Inject, ChangeDetectorRef } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { WharehouseDTO } from 'src/app/shared/model/response/WharehouseDTO';
import { WharehouseDTORequest } from 'src/app/shared/model/request/wharehouseDTORequest';
import { SysBoticaConstant,ARRAY_TYPE_WHAREHOUSE } from 'src/app/shared/constants/sysBoticaConstant';
import { ValidateService } from 'src/app/shared/service/validate.service';

@Component({
  selector: 'app-add-wharehouse',
  templateUrl: './add-wharehouse.component.html',
  styleUrls: ['./add-wharehouse.component.scss']
})
export class AddWharehouseComponent implements OnInit {

  public wharehouses: any[] = [];
  public frmWharehouse!: FormGroup;
  private action!: string;
  private wharehouse!: WharehouseDTORequest;
  private id!: number;
  constructor(
    private _formBuilder: FormBuilder,
    private _dialoRef: MatDialogRef<AddWharehouseComponent>,
    @Inject(MAT_DIALOG_DATA) public data: WharehouseDTO,
    private cdRef: ChangeDetectorRef
  ) { }

  ngOnInit(): void {
    // [formGroup]="frmWharehouse" autocomplete="off"
    this.frmWharehouse = this._formBuilder.group({
      id: [''],
      name: ['', [Validators.required]],
      description: ['', [Validators.required]],
      type: ['', [Validators.required]],
    });
  }

  public setName(event?:any){
    this.frmWharehouse.controls['name'].setValue(ValidateService.validaSoloLetrasEspacio(event.target.value));
  }

  public setDescription(event?:any){
    this.frmWharehouse.controls['description'].setValue(ValidateService.validaSoloLetrasEspacio(event.target.value));
  }

  public onClose(): void {
    this._dialoRef.close(false);
  }

  public ngAfterViewInit(): void {
    this.wharehouses = ARRAY_TYPE_WHAREHOUSE;
    if (this.data) {
      this.frmEditWharehouse(this.data);
    }
    this.cdRef.detectChanges();
  }

  public frmEditWharehouse(row: WharehouseDTO) {
    this.frmWharehouse.setValue({
      id: row.id,
      name: row.name,
      description: row.description,
      type: row.type,
    });
  }

  public onSubmit() {
    if (this.frmWharehouse.valid) {
      this.action = this.frmWharehouse.value.id ? SysBoticaConstant.VC_OPERATION_UPDATE : SysBoticaConstant.VC_OPERATION_ADD;
      this.id = this.frmWharehouse.value.id;
      this.wharehouse = this.frmWharehouse.value;
      this._dialoRef.close({ id: this.id, wharehouse: this.wharehouse, action: this.action });
    }
  }

}
