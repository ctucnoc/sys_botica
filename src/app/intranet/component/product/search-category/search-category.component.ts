import { Component, ViewChild, AfterViewInit } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { CategoryDTO } from 'src/app/shared/model/response/categoryDTO';
import { MatTableDataSource } from '@angular/material/table';
import { MatSort } from '@angular/material/sort';
import { MatPaginator } from '@angular/material/paginator';
import { CategoryService } from 'src/app/shared/service/api/category.service';
import { merge } from 'rxjs';
import { switchMap } from 'rxjs/operators';
import { SysBoticaConstant } from 'src/app/shared/constants/sysBoticaConstant';

@Component({
  selector: 'app-search-category',
  templateUrl: './search-category.component.html',
  styleUrls: ['./search-category.component.scss']
})
export class SearchCategoryComponent implements AfterViewInit {

  public totalElements!: number;
  public categories: CategoryDTO[] = [];
  public listData!: MatTableDataSource<any>;
  public displayedColumns: string[] = ['CATEGORIA', 'DESCRIPCIÓN', 'SELECCIONE'];
  @ViewChild(MatSort) sort!: MatSort;
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  public searchKey!: string;
  constructor(
    private _dialoRef: MatDialogRef<SearchCategoryComponent>,
    private _categoryService: CategoryService
  ) { }

  ngAfterViewInit() {
    this.sort.sortChange.subscribe(() => this.paginator.pageIndex = 0);
    merge(this.sort.sortChange, this.paginator.page)
      .pipe(
        switchMap(() => {
          return this._categoryService.findByDescription(
            this.searchKey,
            this.paginator.pageIndex,
            SysBoticaConstant.PAG_SIZE_INITIAL_SEARCH
          )
        }),
      )
      .subscribe(data => {
        this.categories = data.content;
        this.listData = new MatTableDataSource(this.categories);
        this.totalElements = data.totalElements;
      });
  }

  clear() {
    this.searchKey = '';
  }

  onSearchCategory(event?: any) {
    if (event.key === 'Enter' || event.keyCode === 'Enter') {
      this.findByDescription(this.searchKey);
    }
  }

  selectRow(row: any) {
    this._dialoRef.close({ category: row });
  }

  public onClose(): void {
    this._dialoRef.close(false);
  }

  public findByDescription(key_word: string) {
    this._categoryService.findByDescription(key_word, SysBoticaConstant.PAG_NRO_INITIAL, SysBoticaConstant.PAG_SIZE_INITIAL_SEARCH).subscribe(data => {
      this.categories = data.content;
      this.listData = new MatTableDataSource(this.categories);
      this.totalElements = data.totalElements;
    });
  }

}
