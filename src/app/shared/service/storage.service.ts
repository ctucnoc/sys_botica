import { Injectable } from '@angular/core';

@Injectable({
    providedIn: 'root'
})
export class StorageService {
    constructor() { }
    public setLocalStorage(token: string, key: string): void {
        window.localStorage.removeItem(key);
        window.localStorage.setItem(key, this.encrypt(token));
    }

    public getLocalStorage(key: string): string {
        const tokenValue = localStorage.getItem(key);
        if (tokenValue) {
            return this.decrypt(tokenValue);
        } else {
            return '';
        }
    }

    public encrypt(value: string): string {
        return btoa(escape(value));
    }

    public decrypt(value: string): string {
        return unescape(atob(value));
    }

    public cleanStorageAll() {
        window.localStorage.clear();
    }
}