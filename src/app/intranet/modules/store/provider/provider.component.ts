import { Component, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { settings } from 'src/environments/settings';
import { SysBoticaConstant } from 'src/app/shared/constants/sysBoticaConstant';

@Component({
  selector: 'app-provider',
  templateUrl: './provider.component.html',
  styleUrls: ['./provider.component.scss']
})
export class ProviderComponent implements OnInit {

  constructor(
    private _title: Title,
  ) { }

  ngOnInit(): void {
    this._title.setTitle(settings.appTitle +' '+settings.appVerssion + SysBoticaConstant.TITLE_PAGE_PROVAIDER);
  }

}
