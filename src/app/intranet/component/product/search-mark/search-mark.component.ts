import { Component, OnInit, ViewChild, AfterViewInit } from '@angular/core';
import { MarkDTO } from 'src/app/shared/model/response/markDTO';
import { MatTableDataSource } from '@angular/material/table';
import { MatSort } from '@angular/material/sort';
import { MatPaginator } from '@angular/material/paginator';
import { MarkService } from 'src/app/shared/service/api/mark.service';
import { MatDialogRef } from '@angular/material/dialog';
import { merge } from 'rxjs';
import { switchMap } from 'rxjs/operators';
import { SysBoticaConstant } from 'src/app/shared/constants/sysBoticaConstant';

@Component({
  selector: 'app-search-mark',
  templateUrl: './search-mark.component.html',
  styleUrls: ['./search-mark.component.scss']
})
export class SearchMarkComponent implements AfterViewInit {

  public totalElements!: number;
  public marks: MarkDTO[] = [];
  public listData!: MatTableDataSource<any>;
  public displayedColumns: string[] = ['MARCA', 'SELECCIONE'];
  @ViewChild(MatSort) sort!: MatSort;
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  public searchKey!: string;
  constructor(
    private _dialoRef: MatDialogRef<SearchMarkComponent>,
    private _markService: MarkService
  ) { }

  ngAfterViewInit() {
    this.sort.sortChange.subscribe(() => this.paginator.pageIndex = 0);
    merge(this.sort.sortChange, this.paginator.page)
      .pipe(
        switchMap(() => {
          return this._markService.findAll(
            this.searchKey,
            this.paginator.pageIndex,
            SysBoticaConstant.PAG_SIZE_INITIAL_SEARCH
          )
        }),
      )
      .subscribe(data => {
        this.marks = data.content;
        this.listData = new MatTableDataSource(this.marks);
        this.totalElements = data.totalElements;
      });
  }

  clear() {
    this.searchKey = '';
  }

  onSearchMark(event?: any) {
    if (event.key === 'Enter' || event.keyCode === 'Enter') {
      this.findByDescription(this.searchKey);
    }
  }

  selectRow(row: any) {
    this._dialoRef.close({ mark: row });
  }

  public onClose(): void {
    this._dialoRef.close(false);
  }

  findByDescription(key_word: string) {
    this._markService.findAll(key_word, SysBoticaConstant.PAG_NRO_INITIAL, SysBoticaConstant.PAG_SIZE_INITIAL_SEARCH).subscribe(data => {
      this.marks = data.content;
      this.listData = new MatTableDataSource(this.marks);
      this.totalElements = data.totalElements;
    });
  }

}
