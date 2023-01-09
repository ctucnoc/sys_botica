import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { DashboardDTO } from '../../model/response/dashboardDTO';
import { environment } from 'src/environments/environment';
import { DatePipe } from '@angular/common';

@Injectable({
    providedIn: 'root'
})
export class DashboardService {
    constructor(
        protected _httpClient: HttpClient,
    ) { }

    findByQuantity(date: any, idSubsidiary: number): Observable<DashboardDTO> {
        let queryParameters = new HttpParams();
        queryParameters = queryParameters.append('date', this.getNewDateFormat(date));
        queryParameters = queryParameters.append('idSubsidiary', idSubsidiary);
        return this._httpClient.get<DashboardDTO>(environment.urlBase + '/dasboards/dasboard?' + queryParameters);
    }


    public getNewDateFormat(date: any): string {
        const datePipe: any = new DatePipe('en-US');
        return datePipe.transform(date, 'yyyy-MM-dd');
    }
}