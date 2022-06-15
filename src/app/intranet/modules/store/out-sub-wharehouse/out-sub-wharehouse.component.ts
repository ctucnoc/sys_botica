import { Component, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { settings } from 'src/environments/settings';
import { SysBoticaConstant } from 'src/app/shared/constants/sysBoticaConstant';

@Component({
  selector: 'app-out-sub-wharehouse',
  templateUrl: './out-sub-wharehouse.component.html',
  styleUrls: ['./out-sub-wharehouse.component.scss']
})
export class OutSubWharehouseComponent implements OnInit {

  constructor(
    protected _title: Title,
  ) { }

  ngOnInit(): void {
    this._title.setTitle(settings.appTitle + ' ' + settings.appVerssion + SysBoticaConstant.TITLE_PAGE_OUT_SUB_WAREHOUSE);
  }

}
