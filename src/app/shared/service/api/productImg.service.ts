import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
    providedIn: 'root'
})
export class ProductImgService {
    constructor(
        private _httpClient: HttpClient,
    ) { }

    public findByProduct(idProduct: number): Observable<any> {
        return this._httpClient.get<any>(environment.urlBase + '/productImgs/productImg/' + idProduct);
    }
}