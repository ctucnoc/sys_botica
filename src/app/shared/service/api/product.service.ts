import { Injectable } from '@angular/core';
import { HttpClient, HttpParams, HttpRequest } from '@angular/common/http';
import { ProductDTORequest } from '../../model/request/productDTORequest';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
    providedIn: 'root'
})

export class ProductService {
    constructor(
        private _httpClient: HttpClient
    ) { }

    findByKeyWord(key_word: string, page: number, size: number): Observable<any> {
        let queryParameters = new HttpParams();
        queryParameters = queryParameters.append('page', page);
        queryParameters = queryParameters.append('size', size);
        queryParameters = queryParameters.append('field', '');
        queryParameters = queryParameters.append('order', '');
        queryParameters = queryParameters.append('key_word', key_word);
        return this._httpClient.get<any>(environment.urlBase + '/products/product?' + queryParameters);
    }

    public findById(id: number): Observable<any> {
        return this._httpClient.get<any>(environment.urlBase + '/products/product/' + id);
    }

    public save(product: ProductDTORequest): Observable<any> {
        return this._httpClient.post<any>(environment.urlBase + '/products/product', product);
    }

    public update(product: ProductDTORequest, id: number): Observable<any> {
        return this._httpClient.put<any>(environment.urlBase + '/products/product/' + id, product);
    }

    public delete(id: number): Observable<any> {
        return this._httpClient.patch<any>(environment.urlBase + '/products/product/' + id, null);
    }

    public filterAutoComplete(key_word:string): Observable<any>{
        return this._httpClient.get(environment.urlBase + '/products/product/filter?key_word='+key_word);
    }
}