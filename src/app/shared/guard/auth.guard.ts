import { StorageService } from '../service/storage.service';
import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
import { SysBoticaConstant } from '../constants/sysBoticaConstant';

@Injectable({
    providedIn: 'root'
})
export class AuthGuard implements CanActivate {
    constructor(
        protected _storageService: StorageService,
        protected _router: Router
    ) { }

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
        const preToken = this._storageService.getLocalStorage(SysBoticaConstant.STORAGE_ACCESS_TOKEN);
        const token = preToken != '' ? JSON.parse(preToken) : '';
        console.log('access ->');
        if (token) {
            this._router.navigate(['/intranet/dashboard']);
            return false;
        } else {
            return true;
        }
    }
}