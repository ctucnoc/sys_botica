import { Injectable, Inject } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { CategoryDTORequest } from '../../model/request/categoryDTORequest';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  constructor(
    private _httpClient: HttpClient,
  ) { }

  findByDescription(key_word: string, page: number, size: number): Observable<any> {
    let queryParameters = new HttpParams();
    queryParameters = queryParameters.append('pageNumber', page);
    queryParameters = queryParameters.append('perPage', size);
    queryParameters = queryParameters.append('sortField', '');
    queryParameters = queryParameters.append('sortOrder', '');
    queryParameters = queryParameters.append('key_word', key_word);
    return this._httpClient.get<any>(environment.urlBase + '/categories/category?' + queryParameters);
  }

  public save(category: CategoryDTORequest): Observable<any> {
    return this._httpClient.post<any>(environment.urlBase + '/categories/category', category);
  }

  public findById(id:number): Observable<any> {
    return this._httpClient.get<any>(environment.urlBase + '/categories/category/'+id);
  }

  public update(category: CategoryDTORequest,id:number): Observable<any> {
    return this._httpClient.put<any>(environment.urlBase + '/categories/category/'+id, category);
  }

  public delete(category: CategoryDTORequest,id:number): Observable<any> {
    return this._httpClient.patch<any>(environment.urlBase + '/categories/category/'+id, category);
  }

  public findAll(): Observable<any> {
    return this._httpClient.get<any>(environment.urlBase + '/categories/category/findAll');
  }
}
