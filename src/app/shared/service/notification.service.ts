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
        this.config['panelClass'] = ['notification', 'success','green-snackbar'];
        snackBar.open(msg, '', this.config);
    }

    public warn(msg: string, snackBar: MatSnackBar) {
        this.config['panelClass'] = ['notification','warn','yellon-snackbar'];
        snackBar.open(msg, '', this.config);
    }

    public info(msg: string, snackBar: MatSnackBar) {
        this.config['panelClass'] = ['notification','info','blue-snackbar'];
        snackBar.open(msg, '', this.config);
    }

    public danger(msg: string, snackBar: MatSnackBar) {
        this.config['panelClass'] = ['notification','danger','red-snackbar'];
        snackBar.open(msg, '', this.config);
    }
}