import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SingInComponent } from './component/sing-in/sing-in.component';
import { SingOutComponent } from './component/sing-out/sing-out.component';
import { RouterModule } from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AuthLayoutComponent } from './layout/auth-layout/auth-layout.component';
import { AuthRoutingModule } from './auth-routing.module';
import { SharedModule } from '../shared';
import { NgxMaskModule } from 'ngx-mask';
import { DirectiveModule } from '../shared/directives/directive.module';

const COMPONENTS = [
  SingInComponent,
  SingOutComponent,
  AuthLayoutComponent
];

@NgModule({
  declarations: [
    ...COMPONENTS,
  ],
  imports: [
    CommonModule,
    RouterModule,
    AuthRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    SharedModule,
    DirectiveModule,
    NgxMaskModule.forChild(),
  ]
})
export class AuthModule { }
