import { Injectable } from '@angular/core';
import { HttpClient, HttpParams, HttpRequest } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { UserSubsidiaryDTORequest } from '../../model/request/userSubsidiaryDTORequest';

@Injectable({
    providedIn: 'root'
})

export class UserSubsidiaryService {
    constructor(
        private _httpClient: HttpClient
    ) { }

    findByUserAllSubsidiary(id: number, page: number, size: number): Observable<any> {
        let queryParameters = new HttpParams();
        queryParameters = queryParameters.append('page', page);
        queryParameters = queryParameters.append('size', size);
        queryParameters = queryParameters.append('field', '');
        queryParameters = queryParameters.append('order', '');
        return this._httpClient.get<any>(environment.urlBase + '/users-enterprises/user-enterprise/' + id + '?' + queryParameters);
    }

    save(userSubsidiary: UserSubsidiaryDTORequest) {
        const requ = new HttpRequest('POST', `${environment.urlBase}/users-enterprises/user-enterprise`, userSubsidiary, {
            reportProgress: true,
            responseType: 'json'
        });
        return this._httpClient.request(requ);
    }

    public delete(id: number): Observable<any> {
        return this._httpClient.patch<any>(environment.urlBase + '/users-enterprises/user-enterprise/' + id, null);
    }

    public findSubsidiaryByUserName(username: string): Observable<any> {
        return this._httpClient.get<any>(environment.urlBase + '/users-enterprises/user-enterprise?username=' + username);
    }

    public getIp(): Observable<any>{       
        return this._httpClient.get<any>('https://api.ipify.org/?format=json');
    }
}