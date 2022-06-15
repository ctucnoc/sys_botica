import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
    providedIn: 'root'
})
export class EntryDocumentService {
    constructor(
        protected _httpClient: HttpClient
    ) { }

    findAll():Observable<any>{
        return this._httpClient.get<any>(environment.urlBase + '/entries-documents/entry-document');
    }
}