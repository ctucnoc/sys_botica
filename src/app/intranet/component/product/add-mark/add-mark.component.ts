import { Component, OnInit, Inject, ChangeDetectorRef } from '@angular/core';
import { Mark } from 'src/app/shared/model/request/mark';
import { FormBuilder, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MarkDTO } from 'src/app/shared/model/response/markDTO';
import { SysBoticaConstant } from 'src/app/shared/constants/sysBoticaConstant';

@Component({
  selector: 'app-add-mark',
  templateUrl: './add-mark.component.html',
  styleUrls: ['./add-mark.component.scss']
})
export class AddMarkComponent implements OnInit {
  private action!: string;
  private mark!: Mark;
  private id!: number;
  constructor(
    private _formBuilder: FormBuilder,
    private _dialoRef: MatDialogRef<AddMarkComponent>,
    @Inject(MAT_DIALOG_DATA) public data: MarkDTO,
    private cdRef: ChangeDetectorRef,
  ) { }

  // [formGroup]="frmSelectResultSearch" autocomplete="off"
  frmMark = this._formBuilder.group({
    id: [''],
    name: ['', [Validators.required]],
  });

  ngOnInit(): void {
  }

  ngAfterViewInit(): void {
    if(this.data){
      this.frmEditMark(this.data);
    }
    this.cdRef.detectChanges();
  }

  public onClose(): void {
    this._dialoRef.close(false);
  }

  public onSubmit() {
    if (this.frmMark.valid) {
      this.action = this.frmMark.value.id ? SysBoticaConstant.VC_OPERATION_UPDATE : SysBoticaConstant.VC_OPERATION_ADD;
      this.id = this.frmMark.value.id;
      this.mark = this.frmMark.value;
      this._dialoRef.close({ id: this.id, mark: this.mark, action: this.action });
    }
  }

  public frmEditMark(row: MarkDTO) {
    this.frmMark.setValue({
      id: row.id,
      name: row.name,
    });
  }

}
