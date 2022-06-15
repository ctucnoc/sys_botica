import { Component, OnInit } from '@angular/core';
import { settings } from 'src/environments/settings';
import { Title } from '@angular/platform-browser';
import { SysBoticaConstant } from 'src/app/shared/constants/sysBoticaConstant';

@Component({
  selector: 'app-user-authority',
  templateUrl: './user-authority.component.html',
  styleUrls: ['./user-authority.component.scss']
})
export class UserAuthorityComponent implements OnInit {

  constructor(
    private _title: Title
  ) { }

  ngOnInit(): void {
    this._title.setTitle(settings.appTitle +settings.appVerssion + SysBoticaConstant.TITLE_PAGE_USER_AUTHORITY);
  }

}
