import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { delay } from 'rxjs/operators';

@Injectable({
    providedIn: 'root'
})
export class LoadingService {

    private _loading = new BehaviorSubject<boolean>(false);
    public readonly loading$ = this._loading.asObservable().pipe(delay(1));
    public isRed!: boolean;
    public showText!: boolean;
    public textLoading!: string;

    constructor() {
    }

    show(showText = false, textLoading = 'Cargando...', isRed = false) {
        this._loading.next(true);
        this.isRed = isRed;
        this.showText = showText;
        this.textLoading = textLoading;
    }

    hide() {
        this._loading.next(false);
    }
}