import { Injectable } from '@angular/core';
import { HttpClient, HttpRequest, HttpParams } from '@angular/common/http';
import { UserDTORequest } from '../../model/request/userDTORequest';
import { environment } from 'src/environments/environment';
import { Observable } from 'rxjs';

@Injectable({
    providedIn: 'root'
})
export class UserService {
    constructor(
        private _httpClient: HttpClient
    ) { }

    save(user: UserDTORequest) {
        const requ = new HttpRequest('POST', `${environment.urlBase}/users/user`, user, {
            reportProgress: true,
            responseType: 'json'
        });
        return this._httpClient.request(requ);
    }

    public update(user: UserDTORequest, id: number) {
        const requ = new HttpRequest('PUT', `${environment.urlBase}/users/user/` + id, user, {
            reportProgress: true,
            responseType: 'json'
        });
        return this._httpClient.request(requ);
    }

    findById(id: number): Observable<any> {
        return this._httpClient.get<any>(environment.urlBase + '/users/user/' + id);
    }

    findByFullNameAndUsername(key_word: string, page: number, size: number): Observable<any> {
        let queryParameters = new HttpParams();
        queryParameters = queryParameters.append('page', page);
        queryParameters = queryParameters.append('size', size);
        queryParameters = queryParameters.append('field', '');
        queryParameters = queryParameters.append('order', '');
        queryParameters = queryParameters.append('key_word', key_word);
        return this._httpClient.get<any>(environment.urlBase + '/users/user?' + queryParameters);
    }

    findByAutoCompleteFullName(key_word: string): Observable<any> {
        return this._httpClient.get<any>(environment.urlBase + '/users/user/auto-complete?key_word=' + key_word);
    }
}