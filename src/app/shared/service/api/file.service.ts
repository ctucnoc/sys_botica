import { HttpClient, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

@Injectable({
    providedIn: 'root'
})
export class FileService {

    private url: string = '/files/';
    constructor(
        protected _httpClient: HttpClient
    ) { }

    public save(file: any, id: any) {
        const formData = new FormData();
        formData.append('multipartFile', file);
        const req = new HttpRequest('POST', `${environment.urlBase + this.url}file/${id}`, formData, {
            reportProgress: true,
            responseType: 'json'
        });
        return this._httpClient.request(req);
    }
}