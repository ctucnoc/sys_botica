import { Injectable } from '@angular/core';
import { HttpClient, HttpRequest } from '@angular/common/http';
import { EntryProductDTORequest } from '../../model/request/entryProductDTORequest';
import { environment } from 'src/environments/environment';

@Injectable({
    providedIn:'root'
})
export class EntryProductService{

    constructor(
        protected _httpClient: HttpClient
    ){}

    save(dto: EntryProductDTORequest) {
        const requ = new HttpRequest('POST', `${environment.urlBase}/entries-products/entry-product`, dto, {
            reportProgress: true,
            responseType: 'json'
        });
        return this._httpClient.request(requ);
    }
}