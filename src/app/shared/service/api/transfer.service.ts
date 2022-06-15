import { HttpClient, HttpRequest } from '@angular/common/http';
import { TransferDTORequest } from '../../model/request/transferDTORequest';
import { environment } from 'src/environments/environment';
import { Injectable } from '@angular/core';

@Injectable({
    providedIn:'root'
})
export class TransferService {
    constructor(
        protected _httpClient: HttpClient
    ) { }

    save(dto: TransferDTORequest) {
        const requ = new HttpRequest('POST', `${environment.urlBase}/transfers/transfer`, dto, {
            reportProgress: true,
            responseType: 'json'
        });
        return this._httpClient.request(requ);
    }
}