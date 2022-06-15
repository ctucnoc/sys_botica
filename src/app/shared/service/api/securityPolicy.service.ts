import { HttpClient, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { SecurityPolicyDTORequest } from '../../model/request/securityPolicyDTORequest';

@Injectable({
    providedIn: 'root'
})
export class SecurityPolicyService {
    constructor(
        private _httpClient: HttpClient
    ) { }

    public findByCode(): Observable<any> {
        return this._httpClient.get<any>(environment.urlBase + '/securities-policies/security-policy');
    }

    public update(dto: SecurityPolicyDTORequest, id: number) {
        const requ = new HttpRequest('PUT', `${environment.urlBase}/securities-policies/security-policy/` + id, dto, {
            reportProgress: true,
            responseType: 'json'
        });
        return this._httpClient.request(requ);
    }

}