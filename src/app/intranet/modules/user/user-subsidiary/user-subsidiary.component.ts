import { Component, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { settings } from 'src/environments/settings';
import { SysBoticaConstant } from 'src/app/shared/constants/sysBoticaConstant';

@Component({
  selector: 'app-user-subsidiary',
  templateUrl: './user-subsidiary.component.html',
  styleUrls: ['./user-subsidiary.component.scss']
})
export class UserSubsidiaryComponent implements OnInit {

  constructor(
    private _title: Title,
  ) { }

  ngOnInit(): void {
    this._title.setTitle(settings.appTitle +settings.appVerssion + SysBoticaConstant.TITLE_PAGE_USER_SUBSIDIARY);
  }

}
