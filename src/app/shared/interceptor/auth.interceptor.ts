import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent } from '@angular/common/http';
import { Observable } from 'rxjs';
import { StorageService } from '../service/storage.service';
import { SysBoticaConstant } from '../constants/sysBoticaConstant';
import { Injectable } from '@angular/core';

@Injectable({
    providedIn: 'root'
})
export class AuthInterceptor implements HttpInterceptor {
    constructor(
        protected _storageService: StorageService
    ) { }

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        const preToken = this._storageService.getLocalStorage(SysBoticaConstant.STORAGE_ACCESS_TOKEN);
        const token = preToken != '' ? JSON.parse(preToken) : '';
        let autRque = req;
        if (token != '') {
            autRque = req.clone({
                setHeaders: { authorization: `Bearer ${token}` }
            });
        }
        return next.handle(autRque);
    }
}