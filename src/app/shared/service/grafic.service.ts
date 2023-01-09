import { Injectable } from '@angular/core';

@Injectable({
    providedIn: 'root'
})
export abstract class Grafic {
    constructor() { }

    public static getDataGraficDonut(title: string, data: any[]): Highcharts.Options {
        let obje = {
            chart: {
                plotBackgroundColor: '',
                type: 'pie',
                events: {
                    load: function () {
                        let chart: any = this,
                            offsetLeft = -20,
                            offsetTop = 10,
                            x = chart.plotLeft + chart.plotWidth / 2 + offsetLeft,
                            y = chart.plotTop + chart.plotHeight / 2 + offsetTop,
                            value = 0;

                        chart.series[0].yData.forEach(function (point: any) {
                            value += point;
                        });

                        chart.renderer.text(value, x, y).add().css({
                            fontSize: '30px'
                        }).toFront();
                    }
                }
            },
            title: {
                text: '<h1>' + title + '</h1>', // título
            },
            tooltip: {
                headerFormat: '<b>{point.key}</b><br>',
                pointFormat: '<span style="color:{series.color}">\u25CF</span> {series.name}: {point.y}'
            },
            plotOptions: {
                pie: {
                    allowPointSelect: true,
                    cursor: 'pointer',
                    showInLegend: true,
                    dataLabels: {
                        enabled: true,
                        //format: '<b>{point.name}</b><br>{point.percentage:.1f} %',
                        format: '<br>{point.percentage:.1f} %',
                        distance: -30,
                    }
                }
            },
            series: [{
                type: 'pie',
                size: '80%',
                innerSize: '50%',
                data: data,
            }] as any
        };
        return obje;
    }
}