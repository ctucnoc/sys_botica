import { Component, OnInit, AfterViewInit } from '@angular/core';
import { settings } from 'src/environments/settings';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { WharehouseService } from 'src/app/shared/service/api/wharehouse.service';
import { WharehouseDTO } from 'src/app/shared/model/response/WharehouseDTO';
import { HttpErrorResponse } from '@angular/common/http';
import { AlertService } from 'src/app/shared/service/aler.service';
import { ReportService } from 'src/app/shared/service/api/report.service';
import Swal from "sweetalert2";
import { Subscription } from 'rxjs';
import { StorageService } from 'src/app/shared/service/storage.service';
import { SysBoticaConstant } from 'src/app/shared/constants/sysBoticaConstant';

@Component({
  selector: 'app-product-all-wharehouse',
  templateUrl: './product-all-wharehouse.component.html',
  styleUrls: ['./product-all-wharehouse.component.scss']
})
export class ProductAllWharehouseComponent implements OnInit, AfterViewInit {

  public subwarehouses: WharehouseDTO[] = [];
  public title!: string;
  public frmSearch!: FormGroup;

  protected subscriptions: Array<Subscription> = new Array();
  constructor(
    protected _formBuilder: FormBuilder,
    protected _wharehouseService: WharehouseService,
    protected _alertService: AlertService,
    protected _reportService: ReportService,
    protected _storageService: StorageService,
  ) { }

  ngOnInit(): void {
    this.title = settings.appTitle + ' ' + settings.appVerssion;
    // [formGroup]="frmSearch" autocomplete="off"
    this.frmSearch = this._formBuilder.group({
      idWharehouse: ['', [Validators.required]]
    });
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(subscription => {
      subscription.unsubscribe();
    });
  }

  public ngAfterViewInit(): void {
    const idSubsidiary:any = this._storageService.getLocalStorage(SysBoticaConstant.STORAGE_ID_SUBSIDIARY);
    this.onFindAllWharehouseTypeDistribution(idSubsidiary);
  }

  public onFindAllWharehouseTypeDistribution(id: number) {
    this._wharehouseService.findAllTypeWharehouse(id).subscribe(
      (data) => {
        this.subwarehouses = data.subwarehouses;
      }, (error: HttpErrorResponse) => {

      }
    );
  }

  public onSubmit() {
    if (this.frmSearch.valid) {
      this._alertService.loadingDialog('Descargando...');
      this.subscriptions.push(
        this._reportService.findByProductAllWharehouse(this.frmSearch.value.idWharehouse).subscribe(
          (data) => {
            const filename = 'Vaucher' + '.pdf';
            //saveAs(data, filename);
            let thefile = new Blob([data], { type: 'application/pdf' });
            let url = window.URL.createObjectURL(thefile);
            window.open(url);
            Swal.close();
          }, (error: HttpErrorResponse) => {
            console.log(error.error);
          }
        )
      );
    }
  }

  public clear() {
    this.frmSearch.reset();
  }

}
