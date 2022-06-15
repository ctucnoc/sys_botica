import { Component, OnInit, ChangeDetectorRef, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-confirm-sale',
  templateUrl: './confirm-sale.component.html',
  styleUrls: ['./confirm-sale.component.scss']
})
export class ConfirmSaleComponent implements OnInit {

  constructor(
    private _dialoRef: MatDialogRef<ConfirmSaleComponent>,
    @Inject(MAT_DIALOG_DATA) public url: any,
    private cdRef: ChangeDetectorRef,
  ) { }

  ngOnInit(): void {
  }

  public onClose(): void {
    this._dialoRef.close(false);
  }

}
