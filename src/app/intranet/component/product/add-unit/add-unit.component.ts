import { Component, OnInit, Inject, ChangeDetectorRef } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { UnitDTO } from 'src/app/shared/model/response/unitDTO';
import { UnitDTORequest } from 'src/app/shared/model/request/unitDTORequest';
import { SysBoticaConstant } from 'src/app/shared/constants/sysBoticaConstant';

@Component({
  selector: 'app-add-unit',
  templateUrl: './add-unit.component.html',
  styleUrls: ['./add-unit.component.scss']
})
export class AddUnitComponent implements OnInit {

  private action!: string;
  private unit!: UnitDTORequest;
  private id!: number;
  constructor(
    private _formBuilder: FormBuilder,
    private _dialoRef: MatDialogRef<AddUnitComponent>,
    @Inject(MAT_DIALOG_DATA) public data: UnitDTO,
    private cdRef: ChangeDetectorRef,
  ) { }

  // [formGroup]="frmUnit" autocomplete="off"
  frmUnit = this._formBuilder.group({
    id: [''],
    initials: ['', [Validators.required]],
    description: ['', [Validators.required]],
  });

  ngOnInit(): void {
  }

  ngAfterViewInit(): void {
    this.frmEditUnit(this.data);
    this.cdRef.detectChanges();
  }

  public onClose(): void {
    this._dialoRef.close(false);
  }

  public onSubmit() {
    if (this.frmUnit.valid) {
      this.action = this.frmUnit.value.id ? SysBoticaConstant.VC_OPERATION_UPDATE : SysBoticaConstant.VC_OPERATION_ADD;
      this.id = this.frmUnit.value.id;
      this.unit = this.frmUnit.value;
      this._dialoRef.close({ id: this.id, unit: this.unit,action: this.action });
    }
  }

  public frmEditUnit(row: UnitDTO) {
    this.frmUnit.setValue({
      id: row.id,
      initials: row.initials,
      description: row.description
    });
  }

}
