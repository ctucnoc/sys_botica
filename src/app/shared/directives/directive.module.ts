import { NgModule } from '@angular/core';
import { UpperDirective } from './input/upper.directive';
import { CommonModule } from '@angular/common';

@NgModule({
    declarations: [
        UpperDirective,
    ],
    imports: [
        CommonModule,
    ],
    exports: [
        UpperDirective,
    ]
})
export class DirectiveModule { }