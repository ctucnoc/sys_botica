import { Component, OnInit, Inject, AfterViewInit, ChangeDetectorRef, Output, Input } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';
import { CategoryDTO } from 'src/app/shared/model/response/categoryDTO';
import { CategoryDTORequest } from 'src/app/shared/model/request/categoryDTORequest';
import { SysBoticaConstant } from 'src/app/shared/constants/sysBoticaConstant';
import { ValidateService } from 'src/app/shared/service/validate.service';
import { Subject } from 'rxjs';

@Component({
  selector: 'app-add-category',
  templateUrl: './add-category.component.html',
  styleUrls: ['./add-category.component.scss']
})
export class AddCategoryComponent implements OnInit, AfterViewInit {

  @Input('categoryDto')
  public categoryDto: CategoryDTO = new class implements CategoryDTO {
    id?: number;
    name?: string;
    description?: string;
  };
  @Output('onChange') public onChange: Subject<any> = new Subject();
  private action!: string;
  private category!: CategoryDTORequest;
  private id!: number;
  public frmCategory!: FormGroup;
  constructor(
    private _formBuilder: FormBuilder,
    private _dialoRef: MatDialogRef<AddCategoryComponent>,
    @Inject(MAT_DIALOG_DATA) public data: CategoryDTO,
    private cdRef: ChangeDetectorRef,
  ) { }

  ngOnInit(): void {
    // [formGroup]="frmSelectResultSearch" autocomplete="off"
    this.frmCategory = this._formBuilder.group({
      id: [''],
      name: ['', [Validators.required]],
      description: ['', [Validators.required]],
    });
  }

  ngAfterViewInit(): void {
    if (this.data) {
      this.frmEditCategory(this.data);
    }
    this.cdRef.detectChanges();
  }

  public validatorName(event?:any){
    this.setName(event.target.value);
  }

  public validatorDescription(event?:any){
    this.setDescription(event.target.value);
  }

  public setName(value: string) {
    this.frmCategory.controls['name'].setValue(ValidateService.validaSoloLetrasEspacio(value));
  }

  public setDescription(value: string) {
    this.frmCategory.controls['description'].setValue(ValidateService.validaSoloLetrasEspacio(value));
  }

  public onClose(): void {
    this._dialoRef.close(false);
  }


  public onSubmit() {
    if (this.frmCategory.valid) {
      this.action = this.frmCategory.value.id ? SysBoticaConstant.VC_OPERATION_UPDATE : SysBoticaConstant.VC_OPERATION_ADD;
      this.id = this.frmCategory.value.id;
      this.category = this.frmCategory.value;
      this._dialoRef.close({ id: this.id, category: this.category, action: this.action });
    }
  }

  public frmEditCategory(row: CategoryDTO) {
    this.frmCategory.setValue({
      id: row.id,
      name: row.name,
      description: row.description
    });
  }

}
