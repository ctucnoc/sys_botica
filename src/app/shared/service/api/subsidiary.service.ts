import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
    providedIn: 'root'
})
export class SubsidiaryService {
    constructor(
        private _httpClient: HttpClient
    ) { }

    findByName(name: string, page: number, size: number): Observable<any> {
        let queryParameters = new HttpParams();
        queryParameters = queryParameters.append('page', page);
        queryParameters = queryParameters.append('size', size);
        queryParameters = queryParameters.append('field', '');
        queryParameters = queryParameters.append('order', '');
        queryParameters = queryParameters.append('name', name);
        return this._httpClient.get<any>(environment.urlBase + '/subsidiaries/subsidiary?' + queryParameters);
    }

    findAll(): Observable<any> {
        return this._httpClient.get<any>(environment.urlBase + '/subsidiaries/subsidiary/findAll');
    }

    findByNameAutocomplete(name:string): Observable<any> {
        return this._httpClient.get<any>(environment.urlBase + '/subsidiaries/subsidiary/auto-complete?name='+name);
    }
}