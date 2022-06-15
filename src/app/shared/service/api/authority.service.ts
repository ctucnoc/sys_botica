import { Injectable } from '@angular/core';
import { HttpClient, HttpRequest, HttpParams } from '@angular/common/http';
import { AuthorityDTORequest } from '../../model/request/authorityDTORequest';
import { environment } from 'src/environments/environment';
import { Observable } from 'rxjs';

@Injectable({
    providedIn: 'root'
})
export class AuthorityService {
    constructor(
        private _httpClient: HttpClient
    ) { }

    save(dto: AuthorityDTORequest) {
        const requ = new HttpRequest('POST', `${environment.urlBase}/authorities/authority`, dto, {
            reportProgress: true,
            responseType: 'json'
        });
        return this._httpClient.request(requ);
    }

    public update(dto: AuthorityDTORequest, id: number) {
        const requ = new HttpRequest('PUT', `${environment.urlBase}/authorities/authority/` + id, dto, {
            reportProgress: true,
            responseType: 'json'
        });
        return this._httpClient.request(requ);
    }

    public delete(id: number) {
        const requ = new HttpRequest('PATCH', `${environment.urlBase}/authorities/authority/` + id, {
            reportProgress: true,
            responseType: 'json'
        });
        return this._httpClient.request(requ);
    }

    findById(id: number): Observable<any> {
        return this._httpClient.get<any>(environment.urlBase + '/authorities/authority/' + id);
    }

    findByIduser(id: number, page: number, size: number): Observable<any> {
        let queryParameters = new HttpParams();
        queryParameters = queryParameters.append('page', page);
        queryParameters = queryParameters.append('size', size);
        queryParameters = queryParameters.append('field', '');
        queryParameters = queryParameters.append('order', '');
        return this._httpClient.get<any>(environment.urlBase + '/authorities/authority/user/' + id + '?' + queryParameters);
    }

    findByName(name: string, page: number, size: number): Observable<any> {
        let queryParameters = new HttpParams();
        queryParameters = queryParameters.append('page', page);
        queryParameters = queryParameters.append('size', size);
        queryParameters = queryParameters.append('field', '');
        queryParameters = queryParameters.append('order', '');
        queryParameters = queryParameters.append('name', name ? name : '');
        return this._httpClient.get<any>(environment.urlBase + '/authorities/authority?' + queryParameters);
    }
}