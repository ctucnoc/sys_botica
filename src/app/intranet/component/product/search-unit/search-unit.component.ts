import { Component, OnInit, ViewChild } from '@angular/core';
import { UnitDTO } from 'src/app/shared/model/response/unitDTO';
import { MatTableDataSource } from '@angular/material/table';
import { MatSort } from '@angular/material/sort';
import { MatPaginator } from '@angular/material/paginator';
import { UnitService } from 'src/app/shared/service/api/unit.service';
import { MatDialogRef } from '@angular/material/dialog';
import { merge } from 'rxjs';
import { switchMap } from 'rxjs/operators';
import { SysBoticaConstant } from 'src/app/shared/constants/sysBoticaConstant';

@Component({
  selector: 'app-search-unit',
  templateUrl: './search-unit.component.html',
  styleUrls: ['./search-unit.component.scss']
})
export class SearchUnitComponent implements OnInit {

  public totalElements!: number;
  public units: UnitDTO[] = [];
  public listData!: MatTableDataSource<any>;
  public displayedColumns: string[] = ['UNIDAD DE MEDIDA', 'SELECCIONE'];
  @ViewChild(MatSort) sort!: MatSort;
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  public searchKey!: string;
  constructor(
    private _dialoRef: MatDialogRef<SearchUnitComponent>,
    private _unitService: UnitService
  ) { }

  ngOnInit(): void {
  }

  ngAfterViewInit() {
    this.sort.sortChange.subscribe(() => this.paginator.pageIndex = 0);
    merge(this.sort.sortChange, this.paginator.page)
      .pipe(
        switchMap(() => {
          return this._unitService.findAll(
            this.searchKey,
            this.paginator.pageIndex,
            SysBoticaConstant.PAG_SIZE_INITIAL_SEARCH
          )
        }),
      )
      .subscribe(data => {
        this.units = data.content;
        this.listData = new MatTableDataSource(this.units);
        this.totalElements = data.totalElements;
      });
  }

  clear() {
    this.searchKey = '';
  }

  onSearchUnit(event?: any) {
    if (event.key === 'Enter' || event.keyCode === 'Enter') {
      this.findByDescription(this.searchKey);
    }
  }

  public findByDescription(key_word: any) {
    this._unitService.findAll(key_word, SysBoticaConstant.PAG_NRO_INITIAL, SysBoticaConstant.PAG_SIZE_INITIAL_SEARCH).subscribe(data => {
      this.units = data.content;
      this.listData = new MatTableDataSource(this.units);
      this.totalElements = data.totalElements;
    });
  }

  selectRow(row: any) {
    this._dialoRef.close({ unit: row });
  }

  public onClose(): void {
    this._dialoRef.close(false);
  }

}
