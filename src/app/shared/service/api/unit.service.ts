import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { UnitDTORequest } from '../../model/request/unitDTORequest';

@Injectable({
  providedIn: 'root'
})

export class UnitService {

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
    return this._httpClient.get<any>(environment.urlBase + '/units/unit?' + queryParameters);
  }

  public save(unit: UnitDTORequest): Observable<any> {
    return this._httpClient.post<any>(environment.urlBase + '/units/unit', unit);
  }

  public findById(id: number): Observable<any> {
    return this._httpClient.get<any>(environment.urlBase + '/units/unit/' + id);
  }

  public update(unit: UnitDTORequest, id: number): Observable<any> {
    return this._httpClient.put<any>(environment.urlBase + '/units/unit/' + id, unit);
  }

  public delete(id: number): Observable<any> {
    return this._httpClient.patch<any>(environment.urlBase + '/units/unit/' + id, null);
  }
}