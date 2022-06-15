import { Component, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { settings } from 'src/environments/settings';
import { SysBoticaConstant } from 'src/app/shared/constants/sysBoticaConstant';

@Component({
  selector: 'app-sold-product',
  templateUrl: './sold-product.component.html',
  styleUrls: ['./sold-product.component.scss']
})
export class SoldProductComponent implements OnInit {

  constructor(
    private _title: Title
  ) { }

  ngOnInit(): void {
    this._title.setTitle(settings.appTitle + ' ' + settings.appVerssion + SysBoticaConstant.TITLE_PAGE_SALE_PRODUCT_SEARCH);
  }

}
