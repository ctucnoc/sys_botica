import { StorageService } from '../service/storage.service';
import { Injectable } from '@angular/core';
import { CanActivate, Router, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { SysBoticaConstant } from '../constants/sysBoticaConstant';

@Injectable({
    providedIn: 'root'
})
export class AccessGuard implements CanActivate {
    constructor(
        private _storageService: StorageService,
        private _router: Router
    ) { }

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
        const preToken = this._storageService.getLocalStorage(SysBoticaConstant.STORAGE_ACCESS_TOKEN);
        const token = preToken != '' ? JSON.parse(preToken) : '';
        if (!token) {
            this._router.navigate(['/']);
            return false;
        } else {
            return true;
        }
    }
}