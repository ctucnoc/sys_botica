import { Injectable } from '@angular/core';
import { HttpClient, HttpParams, HttpRequest } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment.prod';
import { WharehouseSubsidiaryDTORequest } from '../../model/request/wharehouseSubsidiaryDTORequest';

@Injectable({
    providedIn:'root'
})
export class WharehouseSubsidiaryService{
    constructor(
        protected _httpClient: HttpClient,
    ){}

    findBySubsidiary(id: number, page: number, size: number): Observable<any> {
        let queryParameters = new HttpParams();
        queryParameters = queryParameters.append('page', page);
        queryParameters = queryParameters.append('size', size);
        queryParameters = queryParameters.append('field', '');
        queryParameters = queryParameters.append('order', '');
        return this._httpClient.get<any>(environment.urlBase + '/wharehouses-subsidiaries/wharehouse-subsidiary/' + id + '?' + queryParameters);
    }

    save(dto: WharehouseSubsidiaryDTORequest) {
        const requ = new HttpRequest('POST', `${environment.urlBase}/wharehouses-subsidiaries/wharehouse-subsidiary`, dto, {
            reportProgress: true,
            responseType: 'json'
        });
        return this._httpClient.request(requ);
    }

    public delete(id: number): Observable<any> {
        return this._httpClient.patch<any>(environment.urlBase + '/wharehouses-subsidiaries/wharehouse-subsidiary/' + id, null);
    }
}