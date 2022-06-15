import { Component, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { SysBoticaConstant } from 'src/app/shared/constants/sysBoticaConstant';
import { settings } from 'src/environments/settings';


@Component({
  selector: 'app-product',
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.scss']
})
export class ProductComponent implements OnInit {

  constructor(
    private _title: Title,
  ) { }

  ngOnInit(): void {
    this._title.setTitle(settings.appTitle +settings.appVerssion + SysBoticaConstant.TITLE_PAGE_PRODUCT);
  }

}
