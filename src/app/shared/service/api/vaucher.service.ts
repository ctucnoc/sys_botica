import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
    providedIn:'root'
})
export class VaucherService{
    constructor(
        protected _httpClient: HttpClient
    ){}

    public findByRePrintVaucher(idSale:number):Observable<any>{
        return this._httpClient.get(environment.urlBase + '/vauchers/vaucher/ticket/'+idSale,{
            responseType: 'blob'
        });
    }
}