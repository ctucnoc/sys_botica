import { Component, OnInit, ViewChild, AfterViewInit } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { MatSort } from '@angular/material/sort';
import { MatPaginator } from '@angular/material/paginator';
import { MarkService } from 'src/app/shared/service/api/mark.service';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { Mark } from 'src/app/shared/model/request/mark';
import { AddMarkComponent } from '../add-mark/add-mark.component';
import { DialogConfirmationComponent } from 'src/app/shared/component/dialog-confirmation/dialog-confirmation.component';
import { SysBoticaConstant } from 'src/app/shared/constants/sysBoticaConstant';
import { merge } from 'rxjs';
import { switchMap } from 'rxjs/operators';
import { settings } from 'src/environments/settings';
import { NotificationService } from 'src/app/shared/service/notification.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-list-mark',
  templateUrl: './list-mark.component.html',
  styleUrls: ['./list-mark.component.scss']
})
export class ListMarkComponent implements OnInit, AfterViewInit {

  public title!: string;
  public mark!: Mark;
  public marks: Mark[] = [];
  public totalElements!: number;
  public listData!: MatTableDataSource<any>;
  public displayedColumns: string[] = ['ID', 'MARCA', 'SELECCIONE'];
  @ViewChild(MatSort) sort!: MatSort;
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  public searchKey!: string;
  constructor(
    private _dialog: MatDialog,
    private _markService: MarkService,
    private _notificationService: NotificationService,
    private _snackBar: MatSnackBar,
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
          return this._markService.findAll(
            this.searchKey ? this.searchKey : '',
            this.paginator.pageIndex,
            this.paginator.pageSize
          )
        }),
      )
      .subscribe(data => {
        this.marks = data.content;
        this.listData = new MatTableDataSource(this.marks);
        this.totalElements = data.totalElements;
      });
  }

  onSearchMark(event?: any) {
    if (event.key === 'Enter' || event.keyCode === 'Enter') {
      this.findByDescription(this.searchKey, SysBoticaConstant.PAG_NRO_INITIAL, SysBoticaConstant.PAG_SIZE_INITIAL);
    }
  }

  onCreater(row?: any) {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.width = "30%";
    dialogConfig.panelClass = "custom-dialog";
    dialogConfig.data = row;
    const dialogRef = this._dialog.open(AddMarkComponent, dialogConfig);
    dialogRef.afterClosed().subscribe(rpta => {
      if (rpta.action === SysBoticaConstant.VC_OPERATION_UPDATE) {
        this.onUpdate(rpta.mark, rpta.id);
      } if (rpta.action === SysBoticaConstant.VC_OPERATION_ADD) {
        this.onSave(rpta.mark);
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

  findByDescription(key_word: string, page: number, size: number) {
    this._markService.findAll(key_word, page, size).subscribe(data => {
      this.marks = data.content;
      this.listData = new MatTableDataSource(this.marks);
      this.totalElements = data.totalElements;
    });
  }

  onSave(mark: Mark) {
    this._markService.save(mark).subscribe(data => {
      this._notificationService.success(SysBoticaConstant.MESSAGE_OBJECT_ADD,this._snackBar);
      this.onFindById(data.id);
    });
  }

  onUpdate(mark: Mark, id: number) {
    this._markService.update(mark, id).subscribe(data => {
      this._notificationService.success(SysBoticaConstant.MESSAGE_OBJECT_UPDATE,this._snackBar);
      this.onFindById(data.id);
    });
  }

  onDeleteById(id: number) {
    this._markService.delete(id).subscribe(data => {
      this._notificationService.success(SysBoticaConstant.MESSAGE_OBJECT_DELETE,this._snackBar);
      this.onFindById(data.id);
    });
  }

  onEraser(){
    this.clear();
    this.clearMarks();
    this.findByDescription(SysBoticaConstant.VC_EMPTY, SysBoticaConstant.PAG_NRO_INITIAL, SysBoticaConstant.PAG_SIZE_INITIAL);
  }

  onFindById(id: number) {
    this._markService.findById(id).subscribe(data => {
      this.clearMarks();
      this.marks.push(data);
      this.listData = new MatTableDataSource(this.marks);
      this.totalElements = SysBoticaConstant.NRO_ELEMENT_DEFAULT;
    });
  }

  clearMarks() {
    this.marks = [];
  }

}
