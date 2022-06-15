import { HttpClient, HttpRequest, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ProviderDTORequest } from '../../model/request/providerDTORequest';
import { environment } from 'src/environments/environment';
import { Observable } from 'rxjs';

@Injectable({
    providedIn: 'root'
})
export class ProviderService {
    constructor(
        private _httpClient: HttpClient
    ) { }

    save(dto: ProviderDTORequest) {
        const requ = new HttpRequest('POST', `${environment.urlBase}/providers/provider`, dto, {
            reportProgress: true,
            responseType: 'json'
        });
        return this._httpClient.request(requ);
    }

    public update(dto: ProviderDTORequest, id: number) {
        const requ = new HttpRequest('PUT', `${environment.urlBase}/providers/provider/` + id, dto, {
            reportProgress: true,
            responseType: 'json'
        });
        return this._httpClient.request(requ);
    }

    public delete(id: number) {
        const requ = new HttpRequest('PATCH', `${environment.urlBase}/providers/provider/` + id, {
            reportProgress: true,
            responseType: 'json'
        });
        return this._httpClient.request(requ);
    }

    findById(id: number): Observable<any> {
        return this._httpClient.get<any>(environment.urlBase + '/providers/provider/' + id);
    }

    findByName(name: string, page: number, size: number): Observable<any> {
        let queryParameters = new HttpParams();
        queryParameters = queryParameters.append('page', page);
        queryParameters = queryParameters.append('size', size);
        queryParameters = queryParameters.append('field', '');
        queryParameters = queryParameters.append('order', '');
        queryParameters = queryParameters.append('name', name ? name : '');
        return this._httpClient.get<any>(environment.urlBase + '/providers/provider?' + queryParameters);
    }

    findAll(page: number, size: number){
        let queryParameters = new HttpParams();
        queryParameters = queryParameters.append('page', page);
        queryParameters = queryParameters.append('size', size);
        queryParameters = queryParameters.append('field', '');
        queryParameters = queryParameters.append('order', '');
        return this._httpClient.get<any>(environment.urlBase + '/providers/provider/findAll?' + queryParameters);
    }
    
}