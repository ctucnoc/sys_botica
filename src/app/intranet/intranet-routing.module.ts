import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DasboardComponent } from './modules/product/dasboard/dasboard.component';
import { IntranetLayoutComponent } from './layout/intranet-layout/intranet-layout.component';
import { CategoryComponent } from './modules/product/category/category.component';
import { MarkComponent } from './modules/product/mark/mark.component';
import { ProductComponent } from './modules/product/product/product.component';
import { UnitComponent } from './modules/product/unit/unit.component';
import { UserComponent } from './modules/user/user/user.component';
import { UserSubsidiaryComponent } from './modules/user/user-subsidiary/user-subsidiary.component';
import { SecurityPolicyComponent } from './modules/user/security-policy/security-policy.component';
import { AuthorityComponent } from './modules/user/authority/authority.component';
import { ProviderComponent } from './modules/store/provider/provider.component';
import { UserAuthorityComponent } from './modules/user/user-authority/user-authority.component';
import { WharehouseComponent } from './modules/store/wharehouse/wharehouse.component';
import { EntryProductComponent } from './modules/store/entry-product/entry-product.component';
import { OutSubWharehouseComponent } from './modules/store/out-sub-wharehouse/out-sub-wharehouse.component';
import { CustomerComponent } from './modules/customer/customer/customer.component';
import { SaleComponent } from './modules/sale/sale/sale.component';
import { SoldProductComponent } from './modules/sale/sold-product/sold-product.component';
import { ReportSaleDateComponent } from './modules/report/report-sale-date/report-sale-date.component';
import { WharehouseSubsidiaryComponent } from './modules/conf/wharehouse-subsidiary/wharehouse-subsidiary.component';

const routes: Routes = [
    {
        path: '', component: IntranetLayoutComponent,
        children: [
            { path: 'dashboard', component: DasboardComponent },
            { path: 'category', component: CategoryComponent },
            { path: 'mark', component: MarkComponent },
            { path: 'product', component: ProductComponent },
            { path: 'unit', component: UnitComponent },
            { path: 'signed-in-redirect', pathMatch: 'full', redirectTo: 'dashboard' },
            { path: 'user', component: UserComponent },
            { path: 'user-subsidiary', component: UserSubsidiaryComponent },
            { path: 'security-policy', component: SecurityPolicyComponent },
            { path: 'authority', component: AuthorityComponent },
            { path: 'provider', component: ProviderComponent },
            { path: 'user-authority', component: UserAuthorityComponent },
            { path: 'wharehouse', component: WharehouseComponent },
            { path: 'entry-product', component: EntryProductComponent },
            { path: 'out-sub-warehouse', component: OutSubWharehouseComponent },
            { path: 'sale', component: SaleComponent },
            { path: 'customer', component: CustomerComponent },
            { path: 'sold-product', component: SoldProductComponent },
            { path: 'report-sale-date', component: ReportSaleDateComponent },
            { path: 'wharehouse-subsidiary', component: WharehouseSubsidiaryComponent },
        ]
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})

export class IntranetRoutingModule { }