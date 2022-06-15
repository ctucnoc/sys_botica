import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { DashboardDTO } from '../../model/response/dashboardDTO';
import { environment } from 'src/environments/environment';

@Injectable({
    providedIn:'root'
})
export class DashboardService{
    constructor(
        protected _httpClient: HttpClient,
    ){}

    findByQuantity():Observable<DashboardDTO>{
        return this._httpClient.get<DashboardDTO>(environment.urlBase + '/dasboards/dasboard');
    }
}