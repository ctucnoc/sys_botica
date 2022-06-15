import { Component, OnInit, Inject, AfterViewInit, ViewChild } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { DtSaleDTO } from 'src/app/shared/model/response/dtSaleDTO';
import { MatTableDataSource } from '@angular/material/table';
import { MatSort } from '@angular/material/sort';
import { MatPaginator } from '@angular/material/paginator';

@Component({
  selector: 'app-dt-sold-product',
  templateUrl: './dt-sold-product.component.html',
  styleUrls: ['./dt-sold-product.component.scss']
})
export class DtSoldProductComponent implements OnInit, AfterViewInit {

  public totalElements!: number;
  public listData!: MatTableDataSource<any>;
  public displayedColumns: string[] = ['PRODUCTO', 'FECHA VENCIMIENTO', 'CANTIDAD', 'PRECIO', 'SELECCIONE'];
  @ViewChild(MatSort) sort!: MatSort;
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  constructor(
    private _dialoRef: MatDialogRef<DtSoldProductComponent>,
    @Inject(MAT_DIALOG_DATA) public data: DtSaleDTO[],
  ) { }

  ngOnInit(): void {
  }

  ngAfterViewInit(): void {
    this.listData = new MatTableDataSource(this.data);
    this.listData.paginator = this.paginator;
    this.totalElements = this.data.length;
  }

  public onClose(): void {
    this._dialoRef.close(false);
  }

}
