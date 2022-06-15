import { HttpClient, HttpRequest, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { WharehouseDTORequest } from '../../model/request/wharehouseDTORequest';
import { environment } from 'src/environments/environment';
import { Observable } from 'rxjs';

@Injectable({
    providedIn: 'root'
})
export class WharehouseService {
    constructor(
        protected _httpClient: HttpClient
    ) { }

    public save(dto: WharehouseDTORequest) {
        const requ = new HttpRequest('POST', `${environment.urlBase}/wharehouses/wharehouse`, dto, {
            reportProgress: true,
            responseType: 'json'
        });
        return this._httpClient.request(requ);
    }

    public update(dto: WharehouseDTORequest, id: number) {
        const requ = new HttpRequest('PUT', `${environment.urlBase}/wharehouses/wharehouse/` + id, dto, {
            reportProgress: true,
            responseType: 'json'
        });
        return this._httpClient.request(requ);
    }

    public delete(id: number) {
        const requ = new HttpRequest('PATCH', `${environment.urlBase}/wharehouses/wharehouse/` + id, null, {
            reportProgress: true,
            responseType: 'json'
        });
        return this._httpClient.request(requ);
    }

    public findById(id: number): Observable<any> {
        return this._httpClient.get<any>(environment.urlBase + '/wharehouses/wharehouse/' + id);
    }

    public findByAllTypeDistribution(id:number): Observable<any> {
        return this._httpClient.get<any>(environment.urlBase + '/wharehouses/wharehouse/finAllDistribution/'+id);
    }

    public findAllTypeWharehouse(id:number): Observable<any> {
        return this._httpClient.get<any>(environment.urlBase + '/wharehouses/wharehouse/findAllType/'+id);
    }

    public findByName(name: string, page: number, size: number): Observable<any> {
        let queryParameters = new HttpParams();
        queryParameters = queryParameters.append('page', page);
        queryParameters = queryParameters.append('size', size);
        queryParameters = queryParameters.append('field', '');
        queryParameters = queryParameters.append('order', '');
        queryParameters = queryParameters.append('name', name);
        return this._httpClient.get<any>(environment.urlBase + '/wharehouses/wharehouse?' + queryParameters);
    }
}