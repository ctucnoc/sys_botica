import { Component, ViewChild, AfterViewInit, OnInit, OnDestroy } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { MatSort } from '@angular/material/sort';
import { MatPaginator } from '@angular/material/paginator';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { AddCategoryComponent } from '../add-category/add-category.component';
import { CategoryDTO } from 'src/app/shared/model/response/categoryDTO';
import { CategoryService } from 'src/app/shared/service/api/category.service';
import { merge, Subscription } from 'rxjs';
import { switchMap } from 'rxjs/operators';
import { CategoryDTORequest } from 'src/app/shared/model/request/categoryDTORequest';
import { DialogConfirmationComponent } from 'src/app/shared/component/dialog-confirmation/dialog-confirmation.component';
import { SysBoticaConstant } from 'src/app/shared/constants/sysBoticaConstant';
import { settings } from 'src/environments/settings';
import { HttpErrorResponse } from '@angular/common/http';
import { AlertService } from 'src/app/shared/service/aler.service';
import Swal from "sweetalert2";

@Component({
  selector: 'app-list-category',
  templateUrl: './list-category.component.html',
  styleUrls: ['./list-category.component.scss']
})
export class ListCategoryComponent implements OnInit, AfterViewInit, OnDestroy {

  public title!: string;
  public totalElements!: number;
  public category!: CategoryDTO;
  public categories: CategoryDTO[] = [];
  public listData!: MatTableDataSource<any>;
  public displayedColumns: string[] = ['CATEGORIA', 'DESCRIPCIÓN', 'SELECCIONE'];
  @ViewChild(MatSort) sort!: MatSort;
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  public searchKey!: string;

  protected subscripstions: Array<Subscription> = new Array();
  constructor(
    private _dialog: MatDialog,
    private _categoryService: CategoryService,
    private _alertService: AlertService,
  ) { }

  ngOnInit(): void {
    this.title = settings.appTitle + ' ' + settings.appVerssion;
  }

  ngOnDestroy(): void {
    this.subscripstions.forEach(subscription => {
      subscription.unsubscribe();
    });
  }

  ngAfterViewInit() {
    this.findByDescription(SysBoticaConstant.VC_EMPTY, SysBoticaConstant.PAG_NRO_INITIAL, SysBoticaConstant.PAG_SIZE_INITIAL);
    this.sort.sortChange.subscribe(() => this.paginator.pageIndex = 0);
    merge(this.sort.sortChange, this.paginator.page)
      .pipe(
        switchMap(() => {
          return this._categoryService.findByDescription(
            this.searchKey ? this.searchKey : '',
            this.paginator.pageIndex,
            this.paginator.pageSize
          )
        }),
      )
      .subscribe(data => {
        this.categories = data.content;
        this.listData = new MatTableDataSource(this.categories);
        this.totalElements = data.totalElements;
      });
  }

  onSearchArea(event?: any) {
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
    const dialogRef = this._dialog.open(AddCategoryComponent, dialogConfig);
    dialogRef.afterClosed().subscribe(rpta => {
      if (rpta.action === SysBoticaConstant.VC_OPERATION_UPDATE) {
        this.onUpdate(rpta.category, rpta.id);
      } if (rpta.action === SysBoticaConstant.VC_OPERATION_ADD) {
        this.onSave(rpta.category);
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
          this.onDeleteById(row, row.id);
        }
      });
  }


  clear() {
    this.searchKey = '';
  }

  findByDescription(key_word: string, page: number, size: number) {
    this._alertService.loadingDialog('Buscando...');
    this.subscripstions.push(
      this._categoryService.findByDescription(key_word, page, size).subscribe(
        (data) => {
          this.categories = data.content;
          this.listData = new MatTableDataSource(this.categories);
          this.totalElements = data.totalElements;
          Swal.close();
        },
        (error: HttpErrorResponse) => {
          this.errorDialog();
          Swal.close();
        }
      )
    );
  }

  errorDialog(): void {
    this._alertService.questionDialog('Ha ocurrido un error. Por favor inténtalo nuevamente', '',
      true, false, 'Entendido', '', 'assets/icons/alert-frame.svg').then(() => {
      });
  }

  onSave(category: CategoryDTORequest) {
    this.subscripstions.push(
      this._categoryService.save(category).subscribe(data => {
        this.onFindById(data.id);
      })
    );
  }

  public onEraser() {
    this.clearCategories();
    this.clear();
    this.findByDescription(SysBoticaConstant.VC_EMPTY, SysBoticaConstant.PAG_NRO_INITIAL, SysBoticaConstant.PAG_SIZE_INITIAL);
  }

  onUpdate(category: CategoryDTORequest, id: number) {
    this.subscripstions.push(
      this._categoryService.update(category, id).subscribe(data => {
        this.onFindById(data.id);
      })
    );
  }

  onDeleteById(category: CategoryDTORequest, id: number) {
    this.subscripstions.push(
      this._categoryService.delete(category, id).subscribe(data => {
        this.onFindById(data.id);
      })
    );
  }

  onFindById(id: number) {
    this.subscripstions.push(
      this._categoryService.findById(id).subscribe(data => {
        this.clearCategories();
        this.categories.push(data);
        this.listData = new MatTableDataSource(this.categories);
        this.totalElements = SysBoticaConstant.NRO_ELEMENT_DEFAULT;
      })
    );
  }

  clearCategories() {
    this.categories = [];
  }

}
