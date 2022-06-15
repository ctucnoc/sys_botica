import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { settings } from 'src/environments/settings';
import { AlertService } from 'src/app/shared/service/aler.service';
import { ReportService } from 'src/app/shared/service/api/report.service';
import Swal from "sweetalert2";
import { HttpErrorResponse } from '@angular/common/http';


@Component({
  selector: 'app-item-report-sale-date',
  templateUrl: './item-report-sale-date.component.html',
  styleUrls: ['./item-report-sale-date.component.scss']
})
export class ItemReportSaleDateComponent implements OnInit {

  public title!: string;
  public frmSearch!: FormGroup;
  constructor(
    protected _formBuilder: FormBuilder,
    protected _alertService: AlertService,
    protected _reportService: ReportService
  ) { }

  ngOnInit(): void {
    this.title = settings.appTitle + ' ' + settings.appVerssion;
    // [formGroup]="frmSearch" autocomplete="off"
    this.frmSearch = this._formBuilder.group({
      dateFrom: [''],
      dateTo: [''],
    });
  }

  public clear() { 
    this.frmSearch.reset();
  }

  public onSubmit() {
    this._alertService.loadingDialog('Descargando...');
    this._reportService.findByReportSaleXDate(this.frmSearch.value.dateFrom, this.frmSearch.value.dateTo).subscribe(
      (data) => {
        const filename = 'Vaucher' + '.pdf';
        //saveAs(data, filename);
        let thefile = new Blob([data]);
        let url = window.URL.createObjectURL(thefile);
        window.open(url);
        Swal.close();
      }, (error: HttpErrorResponse) => {
        console.log(error.error);
      }
    );
  }

}
