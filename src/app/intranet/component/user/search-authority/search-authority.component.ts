import { Component, OnInit, Inject, ViewChild, AfterViewInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { AuthorityDTO } from 'src/app/shared/model/response/authorityDTO';
import { MatTableDataSource } from '@angular/material/table';
import { MatSort } from '@angular/material/sort';
import { MatPaginator } from '@angular/material/paginator';
import { AuthorityService } from 'src/app/shared/service/api/authority.service';
import { SysBoticaConstant } from 'src/app/shared/constants/sysBoticaConstant';
import { merge } from 'rxjs';
import { switchMap } from 'rxjs/operators';

@Component({
  selector: 'app-search-authority',
  templateUrl: './search-authority.component.html',
  styleUrls: ['./search-authority.component.scss']
})
export class SearchAuthorityComponent implements OnInit, AfterViewInit {

  public blFlag: boolean = false;
  public totalElements!: number;
  public authorities: AuthorityDTO[] = [];
  public listData!: MatTableDataSource<AuthorityDTO>;
  public displayedColumns: string[] = ['ROL', 'SELECCIONE'];
  @ViewChild(MatSort) sort!: MatSort;
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  constructor(
    private _dialoRef: MatDialogRef<SearchAuthorityComponent>,
    @Inject(MAT_DIALOG_DATA) public id: any,
    private _authorityServive: AuthorityService
  ) { }

  ngOnInit(): void {
  }

  ngAfterViewInit() {
    this.onFindByIduserAllAuthority(this.id, SysBoticaConstant.PAG_NRO_INITIAL, SysBoticaConstant.PAG_SIZE_INITIAL_SEARCH);
    this.sort.sortChange.subscribe(() => this.paginator.pageIndex = 0);
    merge(this.sort.sortChange, this.paginator.page)
      .pipe(
        switchMap(() => {
          return this._authorityServive.findByIduser(
            this.id,
            this.paginator.pageIndex,
            this.paginator.pageSize
          )
        }),
      )
      .subscribe(data => {
        this.authorities = data.content;
        this.listData = new MatTableDataSource(this.authorities);
        this.totalElements = data.totalElements;
      });
  }

  public onClose(): void {
    this._dialoRef.close(false);
  }

  public selectRow(row: AuthorityDTO) {
    let objt = { idUser: this.id, idAuthority: row.id };
    this.blFlag = true;
    this._dialoRef.close({ blFlag: this.blFlag, userAuthority: objt });
  }

  public onFindByIduserAllAuthority(id: any, page: number, size: number) {
    if (id) {
      this._authorityServive.findByIduser(id, page, size).subscribe(data => {
        this.authorities = data.content;
        this.listData = new MatTableDataSource(this.authorities);
        this.totalElements = data.totalElements;
      });
    }
  }

}
