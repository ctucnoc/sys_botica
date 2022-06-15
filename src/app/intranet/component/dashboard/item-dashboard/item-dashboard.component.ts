import { Component, OnInit, AfterViewInit, ViewChild } from '@angular/core';
import { DashboardService } from 'src/app/shared/service/api/dashboard.service';
import { DashboardDTO } from 'src/app/shared/model/response/dashboardDTO';
import { HttpErrorResponse } from '@angular/common/http';
import { AlertService } from 'src/app/shared/service/aler.service';
import Swal from "sweetalert2";
import { SysBoticaConstant } from 'src/app/shared/constants/sysBoticaConstant';
import { BaseChartDirective } from 'ng2-charts';
import { ChartConfiguration, ChartType, ChartData, ChartEvent } from 'chart.js';

@Component({
  selector: 'app-item-dashboard',
  templateUrl: './item-dashboard.component.html',
  styleUrls: ['./item-dashboard.component.scss']
})
export class ItemDashboardComponent implements OnInit, AfterViewInit {

  public barChartType: ChartType = 'bar';
  public dashboard!: DashboardDTO;
  public vcTotalProduct!: string;
  public vcTotalMark!: string;
  public vcTotalProvider!: string;
  public vcTotalUnit!: string;
  public vcTotalCategory!: string;
  public vcCustomer!: String;

  @ViewChild(BaseChartDirective) chart: BaseChartDirective | undefined;
  constructor(
    protected _dashboardService: DashboardService,
    protected _alertService: AlertService,
  ) { }

  ngOnInit(): void {
    this.vcTotalProduct = SysBoticaConstant.MSG_TOTAL_PRODUCT.toUpperCase();
    this.vcTotalMark = SysBoticaConstant.MSG_TOTAL_MARK.toUpperCase();
    this.vcTotalProvider = SysBoticaConstant.MSG_TOTAL_PROVIDER.toUpperCase();
    this.vcTotalUnit = SysBoticaConstant.MSG_TOTAL_UNIT.toUpperCase();
    this.vcTotalCategory = SysBoticaConstant.MSG_TOTAL_CATEGORY.toUpperCase();
    this.vcCustomer = SysBoticaConstant.MSG_TOTAL_CUSTOMER.toUpperCase();
  }

  ngAfterViewInit(): void {
    this.onFindByQuantity();
  }

  public onFindByQuantity() {
    this._alertService.loadingDialog('Cargando...');
    this._dashboardService.findByQuantity()
      .subscribe(
        (data: any) => {
          this.dashboard = data;
          console.log(this.dashboard);
          Swal.close();
        }, (error: HttpErrorResponse) => {

        }
      );
  }

  public barChartOptions: ChartConfiguration['options'] = {
    responsive: true,
    // We use these empty structures as placeholders for dynamic theming.
    scales: {
      x: {},
      y: {
        min: 10
      }
    },
    plugins: {
      legend: {
        display: true,
      },
    }
  };


  public barChartData: ChartData<'bar'> = {
    labels: ['2006', '2007', '2008', '2009', '2010', '2011', '2012'],
    datasets: [
      { data: [65, 59, 80, 81, 56, 55, 40], label: 'Series A' },
      { data: [28, 48, 40, 19, 86, 27, 90], label: 'Series B' }
    ]
  };

  // events
  public chartClicked({ event, active }: { event?: ChartEvent, active?: {}[] }): void {
    console.log(event, active);
  }

  public chartHovered({ event, active }: { event?: ChartEvent, active?: {}[] }): void {
    console.log(event, active);
  }

  public randomize(): void {
    // Only Change 3 values
    this.barChartData.datasets[0].data = [
      Math.round(Math.random() * 100),
      59,
      80,
      Math.round(Math.random() * 100),
      56,
      Math.round(Math.random() * 100),
      40];

    this.chart?.update();
  }


}
