import { Routes, RouterModule } from "@angular/router";
import { NgModule } from '@angular/core';
import { SingInComponent } from './component/sing-in/sing-in.component';
import { SingOutComponent } from './component/sing-out/sing-out.component';

const routes: Routes = [
    {
        path: 'sing-in', component: SingInComponent
    },
    {
        path: '', redirectTo: 'sing-in', pathMatch: 'full'
    },
    {
        path: 'sing-out', component:SingOutComponent
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class AuthRoutingModule { }