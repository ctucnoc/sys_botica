import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
    providedIn: 'root'
})
export class DtSaleService {
    constructor(
        protected _httpClient: HttpClient
    ) { }

    findBySale(idSale: number): Observable<any> {
        return this._httpClient.get<any>(environment.urlBase + '/dt-sales/dt-sale/' + idSale);
    }
}