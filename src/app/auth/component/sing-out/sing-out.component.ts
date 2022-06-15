import { Component, OnInit } from '@angular/core';
import { timer, Subject } from 'rxjs';
import { finalize, takeUntil, takeWhile, tap } from 'rxjs/operators';
import { Router } from '@angular/router';
import { SysBoticaConstant } from 'src/app/shared/constants/sysBoticaConstant';
import { Title } from '@angular/platform-browser';
import { settings } from 'src/environments/settings';

@Component({
  selector: 'app-sing-out',
  templateUrl: './sing-out.component.html',
  styleUrls: ['./sing-out.component.scss']
})
export class SingOutComponent implements OnInit {

  countdown: number = 5;
  countdownMapping: any = {
    '=1': '# Segundo',
    'other': '# Segundos'
  };
  private _unsubscribeAll: Subject<any> = new Subject<any>();
  constructor(
    private _router: Router,
    private _title: Title,
  ) { }

  ngOnInit(): void {
    this._title.setTitle(settings.appTitle +settings.appVerssion + SysBoticaConstant.TITLE_PAGE_SING_OUT);
    timer(1000, 1000)
      .pipe(
        finalize(() => {
          this._router.navigate(['sing-in']);
        }),
        takeWhile(() => this.countdown > 0),
        takeUntil(this._unsubscribeAll),
        tap(() => this.countdown--)
      )
      .subscribe();
  }

  ngOnDestroy(): void {
    // Unsubscribe from all subscriptions
    this._unsubscribeAll.next();
    this._unsubscribeAll.complete();
  }

  goToHome() {
    this._router.navigate([SysBoticaConstant.RESOURCE_PAG_SING_IN]);
  }

}
