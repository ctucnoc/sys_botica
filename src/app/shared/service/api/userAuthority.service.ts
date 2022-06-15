import { Injectable } from '@angular/core';
import { HttpClient, HttpParams, HttpRequest } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { UserAuthorityDTORequest } from '../../model/request/userAuthorityDTORequest';

@Injectable({
    providedIn: 'root'
})
export class UserAuthorityService {
    constructor(
        private _httpClient: HttpClient
    ) { }

    findByIduser(id: number, page: number, size: number): Observable<any> {
        let queryParameters = new HttpParams();
        queryParameters = queryParameters.append('page', page);
        queryParameters = queryParameters.append('size', size);
        queryParameters = queryParameters.append('field', '');
        queryParameters = queryParameters.append('order', '');
        return this._httpClient.get<any>(environment.urlBase + '/users-authorities/user-autority/' + id + '?' + queryParameters);
    }

    save(dto: UserAuthorityDTORequest) {
        const requ = new HttpRequest('POST', `${environment.urlBase}/users-authorities/user-autority`, dto, {
            reportProgress: true,
            responseType: 'json'
        });
        return this._httpClient.request(requ);
    }

    public delete(id: any) {
        const requ = new HttpRequest('PATCH', `${environment.urlBase}/users-authorities/user-autority/` + id, {
            reportProgress: true,
            responseType: 'json'
        });
        return this._httpClient.request(requ);
    }

}