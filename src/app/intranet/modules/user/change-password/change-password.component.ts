import { Component, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { settings } from 'src/environments/settings';
import { SysBoticaConstant } from 'src/app/shared/constants/sysBoticaConstant';

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.scss']
})
export class ChangePasswordComponent implements OnInit {

  constructor(
    private _title: Title,
  ) { }

  ngOnInit(): void {
    this._title.setTitle(settings.appTitle +settings.appVerssion + SysBoticaConstant.TITLE_PAGE_CHANGE_PASSWORD);
  }

}
