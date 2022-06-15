import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
    name: 'firstLetterUpperCase'
})
export class FirstLetterUpperCasePipe implements PipeTransform {

    transform(value: string, ...args: unknown[]): string {
        return value.toLowerCase().replace(/\b[a-z]/g, c => c.toUpperCase());
    }
}