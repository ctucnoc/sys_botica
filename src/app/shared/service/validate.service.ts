import { Injectable } from '@angular/core';

@Injectable({
    providedIn: 'root'
})
export abstract class ValidateService {
    constructor() { }

    public static dateToday() {
        return new Date();
    }

    public static dateYesterday() {
        let date: Date = this.dateToday();
        date.setDate(date.getDate() - 1);
        return date;
    }

    public static firstLetterUpperCase(word: string) {
        return word.toLowerCase().replace(/\b[a-z]/g, c => c.toUpperCase());
    }

    public static validaSoloLetras(name: string): any {
        const anyString = name;
        let char_ref;
        const nro_caracteres = name.length;
        let textAux: string = "";
        if (nro_caracteres > 0) {
            for (let i = 0; i < nro_caracteres; i++) {
                char_ref = anyString.charAt(i);
                if (this.esLetra(char_ref)) {
                    textAux = textAux + char_ref;
                }
            }
            return textAux;
        }
    }

    public static esLetra(caracter: any): boolean {
        if ("abcdefghijklmnopqrstuvwxyz".indexOf(caracter.toLowerCase()) >= 0) {
            return true;
        } else {
            return false;
        }
    }

    public static validaSoloLetrasEspacio(name: string): any {
        const anyString = name.toString();
        let char_ref;
        const nro_caracteres = name.length;
        let textAux = "";
        if (nro_caracteres > 0) {
            for (let i = 0; i < nro_caracteres; i++) {
                char_ref = anyString.charAt(i);
                if (this.esLetraEspacio(char_ref)) {
                    textAux = textAux + char_ref;
                }
            }
            return textAux;
        }
    }
    public static esLetraEspacio(caracter: string) {
        if ("abcdefghijklmnopqrstuvwxyz ".indexOf(caracter.toLowerCase()) >= 0) {
            return true;
        } else {
            return false;
        }
    }
    public static validaSoloNumeros(name: string): any {
        const anyString = name.toString();
        let char_ref;
        const nro_caracteres = name.length;
        let textAux = "";
        if (nro_caracteres > 0) {
            for (let i = 0; i < nro_caracteres; i++) {
                char_ref = anyString.charAt(i);
                if (this.esNumero(char_ref)) {
                    textAux = textAux + char_ref;
                }
            }
            return textAux;
        }
    }

    public static esNumero(caracter: string) {
        if ("1234567890".indexOf(caracter.toLowerCase()) >= 0) {
            return true;
        } else {
            return false;
        }
    }

    public static validaSoloNumerosTelefonico(name: string): any {
        const anyString = name.toString();
        let char_ref;
        const nro_caracteres = name.length;
        let textAux = "";
        if (nro_caracteres > 0) {
            for (let i = 0; i < nro_caracteres; i++) {
                char_ref = anyString.charAt(i);
                if (this.esNumeroTelefonico(char_ref)) {
                    if (((char_ref.indexOf("#") == 0 && i == 0) || (char_ref.indexOf("*") == 0 && i == 0))
                        || ((char_ref.indexOf("#") == -1 && i == 0) || (char_ref.indexOf("*") == -1 && i == 0))) {
                        textAux += char_ref;
                    } else if ((char_ref.indexOf("#") == -1 && char_ref.indexOf("*") == -1 && i > 0)) {
                        textAux += char_ref;
                    }
                }
            }
            return textAux;
        }
    }
    public static esNumeroTelefonico(caracter: string) {
        if ("1234567890#*".indexOf(caracter.toLowerCase()) >= 0) {
            return true;
        } else {
            return false;
        }
    }

    public static validaSoloLetrasNumeros(name: string): any {
        const anyString = name.toString();
        let char_ref;
        const nro_caracteres = name.length;
        let textAux = "";
        if (nro_caracteres > 0) {
            for (let i = 0; i < nro_caracteres; i++) {
                char_ref = anyString.charAt(i);
                if (this.esLetrasNumeros(char_ref)) {
                    textAux = textAux + char_ref;
                }
            }
            return textAux;
        }
    }
    public static esLetrasNumeros(caracter: string) {
        if ("abcdefghijklmnopqrstuvwxyz1234567890".indexOf(caracter.toLowerCase()) >= 0) {
            return true;
        } else {
            return false;
        }
    }

    public static validaSoloLetrasNumerosCaracteresAceptados(name: string): any {
        const anyString = name.toString();
        let char_ref;
        const nro_caracteres = name.length;
        let textAux = "";
        if (nro_caracteres > 0) {
            for (let i = 0; i < nro_caracteres; i++) {
                char_ref = anyString.charAt(i);
                if (this.esLetrasNumerosCaracteresAceptados(char_ref)) {
                    textAux = textAux + char_ref;
                }
            }
            return textAux;
        }
    }
    public static esLetrasNumerosCaracteresAceptados(caracter: string) {
        if ("abcdefghijklmnopqrstuvwxyz1234567890_@/:.-& ".indexOf(caracter.toLowerCase()) >= 0) {
            return true;
        } else {
            return false;
        }
    }


    public static validarCorreoElectronico(name: string): boolean {
        const anyString = name.toString();
        if (!this.esCorreoElectronico(anyString)) {
            return false;
        } else {
            return true;
        }
    }

    public static esCorreoElectronico(correo: string) {
        const splitted = correo.match("^(.+)@(.+)$");
        if (splitted == null) return false;
        if (splitted[1] != null) {
            const regexp_user = /^\"?[\w-_\.]*\"?$/;
            if (splitted[1].match(regexp_user) == null) return false;
        }
        if (splitted[2] != null) {
            const regexp_domain = /^[\w-\.]*\.[A-Za-z]{2,4}$/;
            if (splitted[2].match(regexp_domain) == null) {
                const regexp_ip = /^\[\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3}\]$/;
                if (splitted[2].match(regexp_ip) == null) return false;
            }
            return true;
        }
        return false;
    }
}