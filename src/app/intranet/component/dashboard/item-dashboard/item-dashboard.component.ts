import { Component, OnInit, AfterViewInit } from '@angular/core';
import { DashboardService } from 'src/app/shared/service/api/dashboard.service';
import { DashboardDTO } from 'src/app/shared/model/response/dashboardDTO';
import { HttpErrorResponse } from '@angular/common/http';
import { AlertService } from 'src/app/shared/service/aler.service';
import Swal from "sweetalert2";
import { SysBoticaConstant } from 'src/app/shared/constants/sysBoticaConstant';
import { ValidateService } from 'src/app/shared/service/validate.service';
import { StorageService } from 'src/app/shared/service/storage.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-item-dashboard',
  templateUrl: './item-dashboard.component.html',
  styleUrls: ['./item-dashboard.component.scss']
})
export class ItemDashboardComponent implements OnInit, AfterViewInit {

  public dashboard!: DashboardDTO;
  public vcTotalProduct!: string;
  public vcTotalMark!: string;
  public vcTotalProvider!: string;
  public vcTotalUnit!: string;
  public vcTotalCategory!: string;
  public vcCustomer!: String;
  public nrIdSubsidiary!: number;

  public vcTitleDailySale!: any;
  public vcTitleMonthSale!: any;

  public lstDailySale!: any;
  public lstMonthSale!: any;

  protected subscriptions: Array<Subscription> = new Array();

  constructor(
    protected _dashboardService: DashboardService,
    protected _alertService: AlertService,
    protected _storageService: StorageService
  ) { }

  ngOnInit(): void {
    this.vcTotalProduct = SysBoticaConstant.MSG_TOTAL_PRODUCT.toUpperCase();
    this.vcTotalMark = SysBoticaConstant.MSG_TOTAL_MARK.toUpperCase();
    this.vcTotalProvider = SysBoticaConstant.MSG_TOTAL_PROVIDER.toUpperCase();
    this.vcTotalUnit = SysBoticaConstant.MSG_TOTAL_UNIT.toUpperCase();
    this.vcTotalCategory = SysBoticaConstant.MSG_TOTAL_CATEGORY.toUpperCase();
    this.vcCustomer = SysBoticaConstant.MSG_TOTAL_CUSTOMER.toUpperCase();
    this.vcTitleDailySale = SysBoticaConstant.TITLE_REPORT_SELE_DAY;
    this.vcTitleMonthSale = SysBoticaConstant.TITLE_REPORT_SELE_MONTH;

    //this.nrIdSubsidiary = this._storageService.getLocalStorage();
    this.onFindByQuantity();
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(subscription => {
      subscription.unsubscribe();
    });
  }

  ngAfterViewInit(): void {
  }

  public onFindByQuantity() {
    this._alertService.loadingDialog('Cargando...');
    this.subscriptions.push(
      this._dashboardService.findByQuantity(ValidateService.dateToday(), 1)
        .subscribe(
          (data: any) => {
            this.dashboard = data;
            this.lstDailySale = this.dashboard.lstDailySale;
            this.lstMonthSale = this.dashboard.lstMonthSale;
            Swal.close();
          }, (error: HttpErrorResponse) => {

          }
        )
    );
  }

}
