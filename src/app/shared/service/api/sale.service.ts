import { HttpClient, HttpRequest, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { SaleDTORequest } from '../../model/request/saleDTORequest';
import { environment } from 'src/environments/environment';
import { Observable } from 'rxjs';
import { DatePipe } from '@angular/common';

@Injectable({
    providedIn: 'root'
})
export class SaleService {
    constructor(
        protected _httpClient: HttpClient
    ) { }

    save(dto: SaleDTORequest): Observable<any> {
        return this._httpClient.post(`${environment.urlBase}/sales/sale`, dto, {
            reportProgress: true,
            responseType: 'blob',
        });
    }

    findByAllParameter(params: any, page: number, size: number): Observable<any> {
        let queryParameters = new HttpParams();
        queryParameters = queryParameters.append('page', page);
        queryParameters = queryParameters.append('size', size);
        queryParameters = queryParameters.append('field', '');
        queryParameters = queryParameters.append('order', '');
        queryParameters = queryParameters.append('idcustomer', params.idcustomer);
        queryParameters = queryParameters.append('idproofpayment', params.idproofpayment);
        queryParameters = queryParameters.append('dateFrom', params.dateFrom ? this.getNewDateFormat(params.dateFrom) : '');
        queryParameters = queryParameters.append('dateTo', params.dateTo ? this.getNewDateFormat(params.dateTo) : '');
        return this._httpClient.get<any>(environment.urlBase + '/sales/sale?' + queryParameters);
    }

    public getNewDateFormat(date: any): string {
        const datePipe: any = new DatePipe('en-US');
        return datePipe.transform(date, 'yyyy-MM-dd');
    }
}