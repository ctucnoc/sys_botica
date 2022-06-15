import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Injectable } from '@angular/core';

@Injectable({
    providedIn: 'root'
})
export class DtTransferService {
    constructor(
        protected _httpClient: HttpClient
    ) { }

    findByWordKey(idsubsidiary: number, key_word: string): Observable<any> {
        let queryParameters = new HttpParams();
        queryParameters = queryParameters.append('idsubsidiary', idsubsidiary);
        queryParameters = queryParameters.append('key_word', key_word);
        return this._httpClient.get<any>(environment.urlBase + '/dt-transfers/dt-transfer?' + queryParameters);
    }
}