import { Component, OnInit } from '@angular/core';
import { settings } from 'src/environments/settings';
import { Title } from '@angular/platform-browser';
import { SysBoticaConstant } from 'src/app/shared/constants/sysBoticaConstant';

@Component({
  selector: 'app-report-sale-date',
  templateUrl: './report-sale-date.component.html',
  styleUrls: ['./report-sale-date.component.scss']
})
export class ReportSaleDateComponent implements OnInit {

  constructor(
    private _title: Title
  ) { }

  ngOnInit(): void {
    this._title.setTitle(settings.appTitle + ' ' + settings.appVerssion + SysBoticaConstant.TITLE_PAGE_REPORT_SALE_DATE);
  }

}
