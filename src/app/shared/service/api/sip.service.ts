import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { UserLoinDTORequest } from '../../model/request/userLoinDTORequest';
import { environment } from 'src/environments/environment';
import { Observable } from 'rxjs';

@Injectable({
    providedIn: 'root'
})
export class SipService {
    constructor(
        private _httpClient: HttpClient,
    ) { }

    login(dto: UserLoinDTORequest): Observable<any> {
        return this._httpClient.post<any>(environment.urlBase + '/sip/login', dto);
    }
}