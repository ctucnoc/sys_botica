import { Injectable } from '@angular/core';
import { HttpClient, HttpRequest, HttpParams } from '@angular/common/http';
import { CustomerDTORequest } from '../../model/request/customerDTORequest';
import { environment } from 'src/environments/environment';
import { Observable } from 'rxjs';

@Injectable({
    providedIn: 'root'
})
export class CustomerService {
    constructor(
        protected _httpClient: HttpClient
    ) { }

    save(dto: CustomerDTORequest) {
        const requ = new HttpRequest('POST', `${environment.urlBase}/customers/customer`, dto, {
            reportProgress: true,
            responseType: 'json'
        });
        return this._httpClient.request(requ);
    }

    public update(dto: CustomerDTORequest, id: number) {
        const requ = new HttpRequest('PUT', `${environment.urlBase}/customers/customer/` + id, dto, {
            reportProgress: true,
            responseType: 'json'
        });
        return this._httpClient.request(requ);
    }

    public delete(id: number) {
        const requ = new HttpRequest('PATCH', `${environment.urlBase}/customers/customer/` + id, {
            reportProgress: true,
            responseType: 'json'
        });
        return this._httpClient.request(requ);
    }

    findById(id: number): Observable<any> {
        return this._httpClient.get<any>(environment.urlBase + '/customers/customer/' + id);
    }

    findByNroDocument(nro_document: string): Observable<any> {
        return this._httpClient.get<any>(environment.urlBase + '/customers/customer/nro-document/' + nro_document);
    }

    findByKeyWord(key_word: string, page: number, size: number): Observable<any> {
        let queryParameters = new HttpParams();
        queryParameters = queryParameters.append('page', page);
        queryParameters = queryParameters.append('size', size);
        queryParameters = queryParameters.append('field', '');
        queryParameters = queryParameters.append('order', '');
        queryParameters = queryParameters.append('key_word', key_word);
        return this._httpClient.get<any>(environment.urlBase + '/customers/customer?' + queryParameters);
    }

    findByKeyWordSQL(key_word: string): Observable<any> {
        return this._httpClient.get<any>(environment.urlBase + '/customers/customer/filter?key_word=' + key_word);
    }
}