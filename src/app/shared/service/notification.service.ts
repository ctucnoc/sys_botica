import { Injectable } from '@angular/core';
import { MatSnackBar, MatSnackBarConfig } from '@angular/material/snack-bar';

@Injectable({
    providedIn: 'root'
})

export class NotificationService {
    constructor(
    ) { }

    public config: MatSnackBarConfig = {
        duration: 3000,
        horizontalPosition: 'right',
        verticalPosition: 'top',
        
    }

    public success(msg: string, snackBar: MatSnackBar) {
        this.config['panelClass'] = ['notification', 'success'];
        snackBar.open(msg, '', this.config);
    }

    public warn(msg: string, snackBar: MatSnackBar) {
        this.config['panelClass'] = ['notification', 'warn'];
        snackBar.open(msg, '', this.config);
    }
}