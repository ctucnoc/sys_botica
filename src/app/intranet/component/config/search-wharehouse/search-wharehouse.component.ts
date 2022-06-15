import { Component, OnInit, Inject, AfterViewInit, ViewChild } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { WharehouseDTO } from 'src/app/shared/model/response/WharehouseDTO';
import { WharehouseService } from 'src/app/shared/service/api/wharehouse.service';
import { SysBoticaConstant } from 'src/app/shared/constants/sysBoticaConstant';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { merge } from 'rxjs';
import { switchMap } from 'rxjs/operators';

@Component({
  selector: 'app-search-wharehouse',
  templateUrl: './search-wharehouse.component.html',
  styleUrls: ['./search-wharehouse.component.scss']
})
export class SearchWharehouseComponent implements OnInit, AfterViewInit {

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;
  public totalElements!: number;
  public wharehouses: WharehouseDTO[] = [];
  public searchKey!: string;
  public blFlag!: boolean;
  constructor(
    protected _dialoRef: MatDialogRef<SearchWharehouseComponent>,
    @Inject(MAT_DIALOG_DATA) public id: any,
    protected _wharehouseService: WharehouseService,
  ) { }

  ngOnInit(): void {
  }

  ngAfterViewInit() {
    this.onFindByName(SysBoticaConstant.VC_EMPTY, SysBoticaConstant.PAG_NRO_INITIAL, SysBoticaConstant.PAG_SIZE_INITIAL_SEARCH);
    this.sort.sortChange.subscribe(() => this.paginator.pageIndex = 0);
    merge(this.sort.sortChange, this.paginator.page)
      .pipe(
        switchMap(() => {
          return this._wharehouseService.findByName(
            this.searchKey ? this.searchKey : '',
            this.paginator.pageIndex,
            this.paginator.pageSize
          )
        }),
      )
      .subscribe(data => {
        this.wharehouses = data.content;
        this.totalElements = data.totalElements;
      });
  }

  public onClose(): void {
    this._dialoRef.close(false);
  }

  public onFindByName(name: string, page: number, size: number) {
    this._wharehouseService.findByName(name, page, size).subscribe(
      (data) => {
        this.wharehouses = data.content;
        this.totalElements = data.totalElements;
      }
    );
  }

  public selectRow(row: WharehouseDTO) {
    let objt = { idWharehouse: row.id, idSubsidiary: this.id };
    console.log(objt);
    this.blFlag = true;
    this._dialoRef.close({ blFlag: this.blFlag, dto: objt });
  }

}
