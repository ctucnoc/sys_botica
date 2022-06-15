import { Component, OnInit, ViewChild, AfterViewInit } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { UnitService } from 'src/app/shared/service/api/unit.service';
import { MatTableDataSource } from '@angular/material/table';
import { AddUnitComponent } from '../add-unit/add-unit.component';
import { UnitDTO } from 'src/app/shared/model/response/unitDTO';
import { DialogConfirmationComponent } from 'src/app/shared/component/dialog-confirmation/dialog-confirmation.component';
import { merge } from 'rxjs';
import { switchMap } from 'rxjs/operators';
import { SysBoticaConstant } from 'src/app/shared/constants/sysBoticaConstant';
import { settings } from 'src/environments/settings';

@Component({
  selector: 'app-list-unit',
  templateUrl: './list-unit.component.html',
  styleUrls: ['./list-unit.component.scss']
})
export class ListUnitComponent implements OnInit, AfterViewInit {

  public title!: string;
  public units: UnitDTO[] = [];
  public totalElements!: number;
  public listData!: MatTableDataSource<any>;
  public displayedColumns: string[] = ['SIGLA', 'DESCRIPCIÓN', 'SELECCIONE'];
  @ViewChild(MatSort) sort!: MatSort;
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  public searchKey!: string;
  constructor(
    private _dialog: MatDialog,
    private _unitService: UnitService
  ) { }

  ngOnInit(): void {
    this.title = settings.appTitle + ' ' + settings.appVerssion;
  }

  ngAfterViewInit() {
    this.findByDescription(SysBoticaConstant.VC_EMPTY, SysBoticaConstant.PAG_NRO_INITIAL, SysBoticaConstant.PAG_SIZE_INITIAL);
    this.sort.sortChange.subscribe(() => this.paginator.pageIndex = 0);
    merge(this.sort.sortChange, this.paginator.page)
      .pipe(
        switchMap(() => {
          return this._unitService.findAll(
            this.searchKey ? this.searchKey : '',
            this.paginator.pageIndex,
            this.paginator.pageSize
          )
        }),
      )
      .subscribe(data => {
        this.units = data.content;
        this.listData = new MatTableDataSource(this.units);
        this.totalElements = data.totalElements;
      });
  }

  public onEraser() {
    this.clearUnits();
    this.clear();
    this.findByDescription(SysBoticaConstant.VC_EMPTY, SysBoticaConstant.PAG_NRO_INITIAL, SysBoticaConstant.PAG_SIZE_INITIAL);
  }

  onCreater(row?: any) {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.width = "30%";
    dialogConfig.panelClass = "custom-dialog";
    dialogConfig.data = row;
    const dialogRef = this._dialog.open(AddUnitComponent, dialogConfig);
    dialogRef.afterClosed().subscribe(rpta => {
      if (rpta.action === SysBoticaConstant.VC_OPERATION_UPDATE) {
        this.onUpdate(rpta.unit, rpta.id);
      } if (rpta.action === SysBoticaConstant.VC_OPERATION_ADD) {
        this.onSave(rpta.unit);
      }
    });
  }

  onDelete(row: any) {
    this._dialog
      .open(DialogConfirmationComponent, {
        data: `¿Seguro que quiere eliminar?`
      })
      .afterClosed()
      .subscribe((confirmado: Boolean) => {
        if (confirmado) {
          this.onDeleteById(row.id);
        }
      });
  }

  clear() {
    this.searchKey = '';
  }

  onSearchUnit(event?: any) {
    if (event.key === 'Enter' || event.keyCode === 'Enter') {
      this.findByDescription(this.searchKey, SysBoticaConstant.PAG_NRO_INITIAL, SysBoticaConstant.PAG_SIZE_INITIAL);
    }
  }

  public onSave(row: any) {
    this._unitService.save(row).subscribe(response => {
      this.findById(response.id);
    });
  }

  public onUpdate(row: any, id: number) {
    this._unitService.update(row, id).subscribe(response => {
      this.findById(response.id);
    });
  }

  public onDeleteById(id: number) {
    this._unitService.delete(id).subscribe(response => {
      this.findById(response.id);
    });
  }

  public findByDescription(key_word: any, page: number, size: number) {
    this._unitService.findAll(key_word, page, size).subscribe(data => {
      this.units = data.content;
      this.listData = new MatTableDataSource(this.units);
      this.totalElements = data.totalElements;
    });
  }

  public findById(id: number) {
    this._unitService.findById(id).subscribe(data => {
      this.clearUnits();
      this.units.push(data);
      this.listData = new MatTableDataSource(this.units);
      this.totalElements = SysBoticaConstant.NRO_ELEMENT_DEFAULT;
    });
  }

  public clearUnits() {
    this.units = [];
  }

}
