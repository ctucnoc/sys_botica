import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
    providedIn: 'root'
})
export class DtEntryProductService {
    public urlDtEntryProduct: string = '/dt-entry-products/dt-entry-product';
    constructor(
        protected _httpClient: HttpClient
    ) { }
    findByIdwharehouse(id: number, word_key: string,page: number, size: number): Observable<any> {
        let queryParameters = new HttpParams();
        queryParameters = queryParameters.append('page', page);
        queryParameters = queryParameters.append('size', size);
        queryParameters = queryParameters.append('field', '');
        queryParameters = queryParameters.append('order', '');
        queryParameters = queryParameters.append('word_key', word_key);
        return this._httpClient.get<any>(environment.urlBase + this.urlDtEntryProduct + '/findByIdwharehouse/' + id +'?'+queryParameters);
    }
}