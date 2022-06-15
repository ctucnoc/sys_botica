import { Component, OnInit, Inject, ChangeDetectorRef } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { DtEntryProductDTO } from 'src/app/shared/model/response/dtEntryProductDTO';
import { SysBoticaConstant } from 'src/app/shared/constants/sysBoticaConstant';
import { DtTransferDTORequest } from 'src/app/shared/model/request/dtTransferDTORequest';

@Component({
  selector: 'app-dialog-income-amount',
  templateUrl: './dialog-income-amount.component.html',
  styleUrls: ['./dialog-income-amount.component.scss']
})
export class DialogIncomeAmountComponent implements OnInit {

  private dtEntryProductDTO!: DtEntryProductDTO;
  private tranferDTORequest!: DtTransferDTORequest;
  private action!: string;
  public frmIncomeAmount!: FormGroup;
  constructor(
    private _formBuilder: FormBuilder,
    private _dialoRef: MatDialogRef<DialogIncomeAmountComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any, //DtEntryProductDTO,
    private cdRef: ChangeDetectorRef
  ) { }

  ngOnInit(): void {
    this.dtEntryProductDTO = this.data.row;
    const max: any = this.data.amountControl;
    // [formGroup]="frmIncomeAmount" autocomplete="off"
    this.frmIncomeAmount = this._formBuilder.group({
      purchaseprecio: [{ value: this.dtEntryProductDTO.purchaseprecio, disabled: true }, [Validators.required]],
      amount: ['', [Validators.required, Validators.max(max)]],
      saleprice: ['', [Validators.required]],
    });
  }

  public onClose(): void {
    this._dialoRef.close(false);
  }

  public onSubmit() {
    if (this.frmIncomeAmount.valid) {
      this.action = SysBoticaConstant.VC_OPERATION_ADD;
      this._dialoRef.close({ dtTransfer: this.onDtTransfer(this.dtEntryProductDTO, this.frmIncomeAmount.value.amount, this.frmIncomeAmount.value.saleprice), action: this.action });
    }
  }

  public onDtTransfer(row: DtEntryProductDTO, amount: number, saleprice: number): DtTransferDTORequest {
    let objt = {
      iddtentryproduct: row.id,
      idproduct: row.idProduct,
      productname: row.productName,
      expiratedate: row.expiratedate,
      lotnumber: row.lotnumber,
      amount: amount,
      saleprice: saleprice,
    };
    this.tranferDTORequest = objt;
    return this.tranferDTORequest;
  }

}
