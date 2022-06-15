import { Component, OnInit, Inject, ChangeDetectorRef } from '@angular/core';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA, MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { ProductDTO } from 'src/app/shared/model/response/productDTO';
import { ProductDTORequest } from 'src/app/shared/model/request/productDTORequest';
import { SearchCategoryComponent } from '../search-category/search-category.component';
import { CategoryDTO } from 'src/app/shared/model/response/categoryDTO';
import { SearchMarkComponent } from '../search-mark/search-mark.component';
import { MarkDTO } from 'src/app/shared/model/response/markDTO';
import { SearchUnitComponent } from '../search-unit/search-unit.component';
import { UnitDTO } from 'src/app/shared/model/response/unitDTO';
import { SysBoticaConstant } from 'src/app/shared/constants/sysBoticaConstant';

@Component({
  selector: 'app-add-product',
  templateUrl: './add-product.component.html',
  styleUrls: ['./add-product.component.scss']
})
export class AddProductComponent implements OnInit {

  private action!: string;
  public flReadonly: boolean = true;
  private product!: ProductDTORequest;
  private id!: number;
  public frmProduct!: FormGroup;
  constructor(
    private _formBuilder: FormBuilder,
    private _dialoRef: MatDialogRef<AddProductComponent>,
    private _dialog: MatDialog,
    @Inject(MAT_DIALOG_DATA) public data: ProductDTO,
    private cdRef: ChangeDetectorRef
  ) { }


  ngOnInit(): void {
    // [formGroup]="frmProduct" autocomplete="off"
    this.frmProduct = this._formBuilder.group({
      id: [''],
      name: ['', [Validators.required]],
      summaryname: ['', [Validators.required]],
      isexpiratedate: ['0', [Validators.required]],
      isrefrigeration: ['0', [Validators.required]],
      isbatch: ['0', [Validators.required]],
      isgeneric: ['0', [Validators.required]],
      ismedicine: ['0', [Validators.required]],
      iscontrolled: ['0', [Validators.required]],
      barcode: [''],
      iskit: ['0', [Validators.required]],
      idcategory: ['', [Validators.required]],
      namecategory: [{ value: '', disabled: true }, [Validators.required]],
      idmark: ['', [Validators.required]],
      namemark: [{ value: '', disabled: true }, [Validators.required]],
      idunit: ['', [Validators.required]],
      nameunit: [{ value: '', disabled: true }, [Validators.required]],
    });
  }

  ngAfterViewInit(): void {
    if (this.data) {
      this.frmEditProduct(this.data);
    }
    this.cdRef.detectChanges();
  }

  public onClose(): void {
    this._dialoRef.close(false);
  }

  public onSubmit() {
    if (this.frmProduct.valid) {
      this.action = this.frmProduct.value.id ? SysBoticaConstant.VC_OPERATION_UPDATE : SysBoticaConstant.VC_OPERATION_ADD;
      this.id = this.frmProduct.value.id;
      this.product = this.frmProduct.value;
      console.log(this.frmProduct.value.idcategory);
      this._dialoRef.close({ id: this.id, product: this.product, action: this.action });
    }
  }

  public frmEditProduct(row: ProductDTO) {
    if (row) {
      this.frmProduct.setValue({
        id: row.id,
        name: row.name,
        summaryname: row.summaryname,
        isexpiratedate: row.isexpiratedate,
        isrefrigeration: row.isrefrigeration,
        isbatch: row.isbatch,
        isgeneric: row.isgeneric,
        iskit: row.iskit,
        ismedicine: row.ismedicine,
        iscontrolled: row.iscontrolled,
        barcode: row.barcode,
        idcategory: row.category?.id,
        namecategory: row.category?.name,
        idmark: row.mark?.id,
        namemark: row.mark?.name,
        idunit: row.unit?.id,
        nameunit: row.unit?.name
      });
    }
  }

  public onSearchCategory() {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.width = "55%";
    dialogConfig.height = "52%";
    dialogConfig.panelClass = "custom-dialog";
    const dialogRef = this._dialog.open(SearchCategoryComponent, dialogConfig);
    dialogRef.afterClosed().subscribe(rpta => {
      if (rpta) {
        this.getCategory(rpta.category);
      }
    });
  }

  public onSearchMark() {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.width = "55%";
    dialogConfig.height = "52%";
    dialogConfig.panelClass = "custom-dialog";
    const dialogRef = this._dialog.open(SearchMarkComponent, dialogConfig);
    dialogRef.afterClosed().subscribe(rpta => {
      if (rpta) {
        this.getMark(rpta.mark);
      }
    });
  }

  public onSearchUnit() {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.width = "55%";
    dialogConfig.height = "52%";
    dialogConfig.panelClass = "custom-dialog";
    const dialogRef = this._dialog.open(SearchUnitComponent, dialogConfig);
    dialogRef.afterClosed().subscribe(rpta => {
      if (rpta) {
        this.getUnit(rpta.unit);
      }
    });
  }

  public getCategory(category: CategoryDTO) {
    this.frmProduct.controls['idcategory'].setValue(category.id);
    this.frmProduct.controls['namecategory'].setValue(category.name);
  }

  public getMark(mark: MarkDTO) {
    this.frmProduct.controls['idmark'].setValue(mark.id);
    this.frmProduct.controls['namemark'].setValue(mark.name);
  }

  public getUnit(unit: UnitDTO) {
    this.frmProduct.controls['idunit'].setValue(unit.id);
    this.frmProduct.controls['nameunit'].setValue(unit.initials);
  }

}
