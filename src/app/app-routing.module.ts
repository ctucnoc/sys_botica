import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CoreLayoutComponent } from './core/layout/core-layout/core-layout.component';
import { AUTHROUTES } from './core/route/allRouter';

const routes: Routes = [
  { path: '', component: CoreLayoutComponent, children: AUTHROUTES },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }