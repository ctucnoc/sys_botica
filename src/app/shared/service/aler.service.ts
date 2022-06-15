import { Injectable } from '@angular/core';
import Swal from 'sweetalert2';

@Injectable({
    providedIn: 'root'
})
export class AlertService {

    constructor(
    ) { }

    questionDialog(tittle: string, subTitle: string, showConfirmButton: boolean, showCancelButton: boolean,
        btnConfirmText: string, btnCancelText: string, image = 'assets/icons/file-frame-1.svg'): Promise<any> {
        return new Promise((resolve, reject) => {
            const swalWithBootstrapButtons = Swal.mixin({
                customClass: {
                    confirmButton: 'btn btn-danger btn-block',
                    cancelButton: 'btn btn-link btn-block mt-2'
                },
                buttonsStyling: false
            });
            swalWithBootstrapButtons.fire({
                html: `<p>${tittle}.</p> `,
                imageUrl: image,
                showConfirmButton: showConfirmButton,
                showCloseButton: false,
                showCancelButton: showCancelButton,
                focusConfirm: true,
                confirmButtonText: btnConfirmText,
                cancelButtonText: btnCancelText,
                allowOutsideClick: false,
                allowEscapeKey: false,
                width: 312
            }).then((result) => {
                if (result.isConfirmed) {
                    resolve(true);
                } else if (result.dismiss === Swal.DismissReason.cancel || result.dismiss === Swal.DismissReason.close) {
                    reject(false);
                }
            });
        });
    }

    errorDialog(title = 'Lo sentimos, nuestro sistema ha presentado un inconveniente. Por favor, inténtalo nuevamente.', showConfirmButton = true) {
        Swal.fire({
            position: 'center',
            icon: 'error',
            title: title,
            showConfirmButton: showConfirmButton
        });
    }
    loadingDialog(text = 'Cargando...') {
        Swal.fire({
            showConfirmButton: false,
            showCloseButton: false,
            showCancelButton: false,
            allowOutsideClick: false,
            allowEscapeKey: false,
            loaderHtml: `<img src="assets/icons/loading.svg" alt="" >`,
            footer: `<p>${text}</p>`,
            width: 312
        });
        Swal.showLoading();
    }
}