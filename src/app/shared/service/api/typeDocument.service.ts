import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { TypeDocumentDTO } from '../../model/response/typeDocumentDTO';
import { environment } from 'src/environments/environment';

@Injectable({
    providedIn: 'root'
})
export class TypeDocumentService {
    constructor(
        protected _httpClient: HttpClient
    ) { }

    findById(): Observable<TypeDocumentDTO[]> {
        return this._httpClient.get<TypeDocumentDTO[]>(environment.urlBase + '/types-documents/type-document');
    }
}