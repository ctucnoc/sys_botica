import { Component, OnInit, ViewChild } from '@angular/core';
import { settings } from 'src/environments/settings';
import { MatSort } from '@angular/material/sort';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Subsidiary } from 'src/app/shared/model/response/subsidiary';
import { SubsidiaryService } from 'src/app/shared/service/api/subsidiary.service';
import { debounceTime, switchMap, catchError } from 'rxjs/operators';
import { HttpErrorResponse } from '@angular/common/http';
import { AlertService } from 'src/app/shared/service/aler.service';
import { WharehouseSubsidiaryService } from 'src/app/shared/service/api/wharehouseSubsidiary.service';
import { SysBoticaConstant } from 'src/app/shared/constants/sysBoticaConstant';
import { WharehouseSubsidiaryDTO } from 'src/app/shared/model/response/wharehouseSubsidiaryDTO';
import { merge, of } from 'rxjs';
import { MatDialogConfig, MatDialog } from '@angular/material/dialog';
import { SearchWharehouseComponent } from '../search-wharehouse/search-wharehouse.component';
import { WharehouseSubsidiaryDTORequest } from 'src/app/shared/model/request/wharehouseSubsidiaryDTORequest';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-list-wharehouse-subsidiary',
  templateUrl: './list-wharehouse-subsidiary.component.html',
  styleUrls: ['./list-wharehouse-subsidiary.component.scss']
})
export class ListWharehouseSubsidiaryComponent implements OnInit {

  public wharehouseSubsidiaries: WharehouseSubsidiaryDTO[] = [];
  public subsidiaries: Subsidiary[] = [];
  public totalElements!: number;
  public title!: string;
  public displayedColumns: string[] = ['SUCURSAL', 'ALMACÉN', 'TIPO DE ALMACEN', 'SELECCIONE'];
  @ViewChild(MatSort) sort!: MatSort;
  public dataSource!: MatTableDataSource<any>;
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  public searchKey!: string;
  public frmSubsidiary!: FormGroup;
  constructor(
    private _formBuilder: FormBuilder,
    private _subsidiaryService: SubsidiaryService,
    private _alertService: AlertService,
    private _wharehouseSubsidiaryService: WharehouseSubsidiaryService,
    private _dialog: MatDialog,
  ) { }

  ngOnInit(): void {
    this.title = settings.appTitle + ' ' + settings.appVerssion;

    // [formGroup]="frmSubsidiary" autocomplete="off"
    this.frmSubsidiary = this._formBuilder.group({
      id: ['', [Validators.required]],
      name: ['', [Validators.required]],
    });

    this.frmSubsidiary.controls['name'].valueChanges
      .pipe(debounceTime(400))
      .subscribe(response => {
        if (response && response.length) {
          this.onFindAutoCompleteName(response);
        } else {
          this.clearSubsidiary();
        }
      })
  }

  ngAfterViewInit() {
    this.sort.sortChange.subscribe(() => this.paginator.pageIndex = 0);
    merge(this.sort.sortChange, this.paginator.page)
      .pipe(
        switchMap(() => {
          return this._wharehouseSubsidiaryService.findBySubsidiary(
            this.frmSubsidiary.value.id,
            this.paginator.pageIndex,
            this.paginator.pageSize
          )
        }),
      )
      .subscribe(data => {
        this.wharehouseSubsidiaries = data.content;
        this.dataSource = new MatTableDataSource(this.wharehouseSubsidiaries);
        this.totalElements = data.totalElements;
      });
  }

  public onSelect(row: Subsidiary) {
    this.setFrmSubsidiary(row);
    this.onFindBySubsidiary(row.id);
  }

  public setFrmSubsidiary(row: Subsidiary) {
    this.frmSubsidiary.setValue({
      id: row.id,
      name: row.name
    });
  }

  public onFindAutoCompleteName(name: any) {
    this._subsidiaryService.findByNameAutocomplete(name)
      .subscribe(
        (data: any) => {
          this.subsidiaries = data;
        },
        (error: HttpErrorResponse) => {

        }
      );
  }

  errorDialog(): void {
    this._alertService.questionDialog('Ha ocurrido un error. Por favor inténtalo nuevamente', '',
      true, false, 'Entendido', '', 'assets/icons/alert-frame.svg').then(() => {
      });
  }

  public clearSubsidiary(): Subsidiary[] {
    return this.subsidiaries = [];;
  }

  public onFindBySubsidiary(id: any) {
    this._wharehouseSubsidiaryService.findBySubsidiary(id, SysBoticaConstant.PAG_NRO_INITIAL, SysBoticaConstant.PAG_SIZE_INITIAL).subscribe(data => {
      if (data) {
        this.wharehouseSubsidiaries = data.content;
        this.dataSource = new MatTableDataSource(this.wharehouseSubsidiaries);
        this.totalElements = data.totalElements;
      }
    });
  }

  public onDisablePlus(): boolean {
    return !this.frmSubsidiary.valid;
  }

  public onCreater() {
    if (this.frmSubsidiary.value.id) {
      const dialogConfig = new MatDialogConfig();
      dialogConfig.disableClose = true;
      dialogConfig.autoFocus = true;
      dialogConfig.width = "55%";
      dialogConfig.height = "48%";
      dialogConfig.panelClass = "custom-dialog";
      dialogConfig.data = this.frmSubsidiary.value.id;
      const dialogRef = this._dialog.open(SearchWharehouseComponent, dialogConfig);
      dialogRef.afterClosed().subscribe((resp: any) => {
        if (resp.blFlag) {
          this.onSaveWharehouseSubsidiary(resp.dto);
        }
      });
    }
  }

  public onSaveWharehouseSubsidiary(dto: WharehouseSubsidiaryDTORequest) {
    this._wharehouseSubsidiaryService.save(dto)
      .pipe(
        catchError((error: HttpErrorResponse) => {
          if (error.status === 422) {
            this.userExistDialog();
          } else {
            this.errorDialog();
          }
          return of({});
        })
      )
      .subscribe(
        (resp: any) => {
          if (resp.status === 200) {
            this.onFindBySubsidiary(dto.idSubsidiary);
          }
        }
      );
  }

  public userExistDialog(): void {
    this._alertService.questionDialog('Almacén ya Asignado', '', true, false, 'Entendido', '', 'assets/icons/alert-frame.svg').then(() => {
    });
  }

  public clear() {
    this.clearSubsidiary();
  }

  public clearWharehouseSubsidiary(): WharehouseSubsidiaryDTO[] {
    return this.wharehouseSubsidiaries = [];
  }

  public onEraser() {
    this.clear();
    this.clearWharehouseSubsidiary();
    this.dataSource = new MatTableDataSource(this.wharehouseSubsidiaries);
  }

  onDelete(id: number) {
    this._alertService.questionDialog('¿Seguro que quiere eliminar?', '', true, true, 'Aceptar', 'Cancelar', 'assets/icons/alert-frame.svg').then(() => {
      this.delete(id);
    }, () => {
      console.log('celia :: ');
    });
  }

  public delete(id: number) {
    this._wharehouseSubsidiaryService.delete(id).subscribe(data => {
      if (data) {
        this.onFindBySubsidiary(this.frmSubsidiary.controls['id'].value);
      }
    });
  }

}
