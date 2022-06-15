import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { DatePipe } from '@angular/common';
import { ValidateService } from '../validate.service';


@Injectable({
    providedIn: 'root'
})
export class ReportService {
    constructor(
        protected _httpClient: HttpClient
    ) { }
    public findByReportSaleXDate(dateFrom: string, dateTo: string): Observable<any> {
        let queryParameters = new HttpParams();
        queryParameters = queryParameters.append('dateFrom', dateFrom ? this.getNewDateFormat(dateFrom) : this.getNewDateFormat(ValidateService.dateToday().toString()));
        queryParameters = queryParameters.append('dateTo', dateTo ? this.getNewDateFormat(dateTo) : this.getNewDateFormat(ValidateService.dateToday().toString()));
        return this._httpClient.get(environment.urlBase + '/reports/report/sale-x-date?'+queryParameters, {
            responseType: 'blob'
        });
    }

    public getNewDateFormat(date: any): string {
        const datePipe: any = new DatePipe('en-US');
        return datePipe.transform(date, 'yyyy-MM-dd');
    }
}