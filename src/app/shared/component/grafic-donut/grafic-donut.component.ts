import { Component, OnInit, Input } from '@angular/core';
import * as Highcharts from 'highcharts';
import { GraficUtilService } from '../../utils/graficUtils.service';

@Component({
  selector: 'app-grafic-donut',
  templateUrl: './grafic-donut.component.html',
  styleUrls: ['./grafic-donut.component.scss']
})
export class GraficDonutComponent implements OnInit {

  public Highcharts: typeof Highcharts = Highcharts;
  public chartOptions!: Highcharts.Options;
  @Input() data!: any;
  @Input() title!: any;
  @Input() color!: any;
  constructor() { }

  ngOnInit(): void {
    this.chartOptions = GraficUtilService.getDataGraficDonut(this.title, this.data);
  }

}
