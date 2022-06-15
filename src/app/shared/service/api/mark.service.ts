import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { MarkDTORequest } from '../../model/request/markDTORequest';

@Injectable({
    providedIn: 'root'
})

export class MarkService {
    constructor(
        private _httpClient: HttpClient
    ) { }

    findAll(key_word: string, page: number, size: number): Observable<any> {
        let queryParameters = new HttpParams();
        queryParameters = queryParameters.append('page', page);
        queryParameters = queryParameters.append('size', size);
        queryParameters = queryParameters.append('field', '');
        queryParameters = queryParameters.append('order', '');
        queryParameters = queryParameters.append('key_word', key_word);
        return this._httpClient.get<any>(environment.urlBase + '/marks/mark?' + queryParameters);
      }
    
      public save(mark: MarkDTORequest): Observable<any> {
        return this._httpClient.post<any>(environment.urlBase + '/marks/mark', mark);
      }
    
      public findById(id: number): Observable<any> {
        return this._httpClient.get<any>(environment.urlBase + '/marks/mark/' + id);
      }
    
      public update(mark: MarkDTORequest, id: number): Observable<any> {
        return this._httpClient.put<any>(environment.urlBase + '/marks/mark/' + id, mark);
      }
    
      public delete(id: number): Observable<any> {
        return this._httpClient.patch<any>(environment.urlBase + '/marks/mark/' + id,null);
      }
}