import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CoreLayoutComponent } from './layout/core-layout/core-layout.component';
import { RouterModule } from '@angular/router';


const COMPONENTS = [
  CoreLayoutComponent
];

@NgModule({
  declarations: [
    ...COMPONENTS
  ],
  imports: [
    CommonModule,
    RouterModule
  ]
})
export class CoreModule { }
