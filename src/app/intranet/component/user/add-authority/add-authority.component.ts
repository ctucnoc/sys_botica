import { Component, OnInit, Inject, ChangeDetectorRef } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { AuthorityDTORequest } from 'src/app/shared/model/request/authorityDTORequest';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { AuthorityDTO } from 'src/app/shared/model/response/authorityDTO';
import { SysBoticaConstant } from 'src/app/shared/constants/sysBoticaConstant';

@Component({
  selector: 'app-add-authority',
  templateUrl: './add-authority.component.html',
  styleUrls: ['./add-authority.component.scss']
})
export class AddAuthorityComponent implements OnInit {

  private action!: string;
  public blReadonly!: boolean;
  private authority!: AuthorityDTORequest;
  private id!: number;
  constructor(
    private _formBuilder: FormBuilder,
    private _dialoRef: MatDialogRef<AddAuthorityComponent>,
    @Inject(MAT_DIALOG_DATA) public data: AuthorityDTO,
    private cdRef: ChangeDetectorRef
  ) { }

  // [formGroup]="frmAuthority" autocomplete="off"
  frmAuthority = this._formBuilder.group({
    id: [''],
    name: ['', [Validators.required]],
    description: [''],
  });

  ngOnInit(): void {
  }

  public onClose(): void {
    this._dialoRef.close(false);
  }

  public ngAfterViewInit(): void {
    this.blReadonly = this.data ? true : false;
    if (this.data) {
      this.frmEditAuthority(this.data);
    }
    this.cdRef.detectChanges();
  }

  public frmEditAuthority(row: AuthorityDTO) {
    this.frmAuthority.setValue({
      id: row.id,
      name: row.name,
      description: row.description,
    });
  }

  public onSubmit() {
    if (this.frmAuthority.valid) {
      this.action = this.frmAuthority.value.id ? SysBoticaConstant.VC_OPERATION_UPDATE : SysBoticaConstant.VC_OPERATION_ADD;
      this.id = this.frmAuthority.value.id;
      this.authority = this.frmAuthority.value;
      this._dialoRef.close({ id: this.id, authority: this.authority, action: this.action });
    }
  }

}
