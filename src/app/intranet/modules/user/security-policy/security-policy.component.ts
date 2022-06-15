import { Component, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { settings } from 'src/environments/settings';
import { SysBoticaConstant } from 'src/app/shared/constants/sysBoticaConstant';

@Component({
  selector: 'app-security-policy',
  templateUrl: './security-policy.component.html',
  styleUrls: ['./security-policy.component.scss']
})
export class SecurityPolicyComponent implements OnInit {

  constructor(
    private _title: Title,
  ) { }

  ngOnInit(): void {
    this._title.setTitle(settings.appTitle +settings.appVerssion + SysBoticaConstant.TITLE_PAGE_SECURITY_POLICY);
  }

}
