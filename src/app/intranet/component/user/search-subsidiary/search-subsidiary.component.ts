import { Component, OnInit, Inject, ViewChild } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { UserDTO } from 'src/app/shared/model/response/userDTO';
import { Subsidiary } from 'src/app/shared/model/response/subsidiary';
import { MatTableDataSource } from '@angular/material/table';
import { MatSort } from '@angular/material/sort';
import { MatPaginator } from '@angular/material/paginator';
import { SubsidiaryService } from 'src/app/shared/service/api/subsidiary.service';
import { SysBoticaConstant } from 'src/app/shared/constants/sysBoticaConstant';
import { UserSubsidiaryDTORequest } from 'src/app/shared/model/request/userSubsidiaryDTORequest';
import { UserSubsidiaryService } from 'src/app/shared/service/api/userSubsidiary.service';

@Component({
  selector: 'app-search-subsidiary',
  templateUrl: './search-subsidiary.component.html',
  styleUrls: ['./search-subsidiary.component.scss']
})
export class SearchSubsidiaryComponent implements OnInit {

  public blFlag:boolean = false;
  public totalElements!: number;
  public subsidiaries: Subsidiary[] = [];
  public listData!: MatTableDataSource<any>;
  public displayedColumns: string[] = ['SUCURSAL', 'SELECCIONE'];
  @ViewChild(MatSort) sort!: MatSort;
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  public searchKey!: string;
  constructor(
    private _dialoRef: MatDialogRef<SearchSubsidiaryComponent>,
    @Inject(MAT_DIALOG_DATA) public data: UserDTO,
    private _subsidiaryService: SubsidiaryService,
  ) { }

  ngOnInit(): void {
    console.log(this.data.id);
  }

  public selectRow(row: Subsidiary) {
    let objt = { idUser: this.data.id, idSubsidiary: row.id };
    this.blFlag = true;
    this._dialoRef.close({ blFlag: this.blFlag, userSubsidiary: objt });
  }

  public onClose(): void {
    this._dialoRef.close(false);
  }

  public clear() {
    return this.searchKey = '';
  }

  public onSearchSubsidiary(event?: any) {
    if (event.key === 'Enter' || event.keyCode === 'Enter') {
      this.onFindName(this.searchKey);
    }
  }

  public onFindName(name: string) {
    this._subsidiaryService.findByName(name, SysBoticaConstant.PAG_NRO_INITIAL, SysBoticaConstant.PAG_SIZE_INITIAL_SEARCH).subscribe(data => {
      if (data) {
        this.subsidiaries = data.content;
        this.listData = new MatTableDataSource(this.subsidiaries);
        this.totalElements = data.totalElements;
      }
    });
  }

}
