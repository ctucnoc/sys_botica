import { Component, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { settings } from 'src/environments/settings';
import { SysBoticaConstant } from 'src/app/shared/constants/sysBoticaConstant';

@Component({
  selector: 'app-entry-product',
  templateUrl: './entry-product.component.html',
  styleUrls: ['./entry-product.component.scss']
})
export class EntryProductComponent implements OnInit {

  constructor(
    private _title: Title,
  ) { }

  ngOnInit(): void {
    this._title.setTitle(settings.appTitle +' '+settings.appVerssion + SysBoticaConstant.TITLE_PAGE_ENTRY_PRODUCT);
  }

}
