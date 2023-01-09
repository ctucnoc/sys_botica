import { Component, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { settings } from 'src/environments/settings';
import { SysBoticaConstant } from 'src/app/shared/constants/sysBoticaConstant';

@Component({
  selector: 'app-report-product-wharehouse',
  templateUrl: './report-product-wharehouse.component.html',
  styleUrls: ['./report-product-wharehouse.component.scss']
})
export class ReportProductWharehouseComponent implements OnInit {

  constructor(
    private _title: Title
  ) { }

  ngOnInit(): void {
    this._title.setTitle(settings.appTitle + ' ' + settings.appVerssion + SysBoticaConstant.TITLE_PAGE_REPORT_PRODUCT_WHAREHOUSE);
  }

}
