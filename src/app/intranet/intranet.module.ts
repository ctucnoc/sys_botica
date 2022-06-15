import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { IntranetLayoutComponent } from './layout/intranet-layout/intranet-layout.component';
import { DasboardComponent } from './modules/product/dasboard/dasboard.component';
import { IntranetRoutingModule } from './intranet-routing.module';
import { SharedModule } from '../shared';
import { CategoryComponent } from './modules/product/category/category.component';
import { AddCategoryComponent } from './component/product/add-category/add-category.component';
import { ListCategoryComponent } from './component/product/list-category/list-category.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ProductComponent } from './modules/product/product/product.component';
import { MarkComponent } from './modules/product/mark/mark.component';
import { UnitComponent } from './modules/product/unit/unit.component';
import { AddUnitComponent } from './component/product/add-unit/add-unit.component';
import { ListUnitComponent } from './component/product/list-unit/list-unit.component';
import { AddMarkComponent } from './component/product/add-mark/add-mark.component';
import { ListMarkComponent } from './component/product/list-mark/list-mark.component';
import { AddProductComponent } from './component/product/add-product/add-product.component';
import { ListProductComponent } from './component/product/list-product/list-product.component';
import { SearchCategoryComponent } from './component/product/search-category/search-category.component';
import { SearchMarkComponent } from './component/product/search-mark/search-mark.component';
import { SearchUnitComponent } from './component/product/search-unit/search-unit.component';
import { UserComponent } from './modules/user/user/user.component';
import { ListUserComponent } from './component/user/list-user/list-user.component';
import { AddUserComponent } from './component/user/add-user/add-user.component';
import { UserSubsidiaryComponent } from './modules/user/user-subsidiary/user-subsidiary.component';
import { ListUserSubsidiaryComponent } from './component/user/list-user-subsidiary/list-user-subsidiary.component';
import { SearchSubsidiaryComponent } from './component/user/search-subsidiary/search-subsidiary.component';
import { SecurityPolicyComponent } from './modules/user/security-policy/security-policy.component';
import { SeeSecurityPolicyComponent } from './component/user/see-security-policy/see-security-policy.component';
import { ListAuthorityComponent } from './component/user/list-authority/list-authority.component';
import { AddAuthorityComponent } from './component/user/add-authority/add-authority.component';
import { AuthorityComponent } from './modules/user/authority/authority.component';
import { ProviderComponent } from './modules/store/provider/provider.component';
import { AddProviderComponent } from './component/store/add-provider/add-provider.component';
import { ListProviderComponent } from './component/store/list-provider/list-provider.component';
import { UserAuthorityComponent } from './modules/user/user-authority/user-authority.component';
import { ListUserAuthorityComponent } from './component/user/list-user-authority/list-user-authority.component';
import { SearchAuthorityComponent } from './component/user/search-authority/search-authority.component';
import { WharehouseComponent } from './modules/store/wharehouse/wharehouse.component';
import { AddWharehouseComponent } from './component/store/add-wharehouse/add-wharehouse.component';
import { ListWharehouseComponent } from './component/store/list-wharehouse/list-wharehouse.component';
import { EntryProductComponent } from './modules/store/entry-product/entry-product.component';
import { AddEntryProductComponent } from './component/store/add-entry-product/add-entry-product.component';
import { DetailEntryProductComponent } from './component/store/detail-entry-product/detail-entry-product.component';
import { OutSubWharehouseComponent } from './modules/store/out-sub-wharehouse/out-sub-wharehouse.component';
import { AddOutSubWarehouseComponent } from './component/store/add-out-sub-warehouse/add-out-sub-warehouse.component';
import { DialogIncomeAmountComponent } from './component/store/dialog-income-amount/dialog-income-amount.component';
import { SaleComponent } from './modules/sale/sale/sale.component';
import { MakeSaleComponent } from './component/sale/make-sale/make-sale.component';
import { CustomerComponent } from './modules/customer/customer/customer.component';
import { ListCustomerComponent } from './component/customer/list-customer/list-customer.component';
import { AddCustomerComponent } from './component/customer/add-customer/add-customer.component';
import { SoldProductComponent } from './modules/sale/sold-product/sold-product.component';
import { SearchSoldProductComponent } from './component/sale/search-sold-product/search-sold-product.component';
import { DtSoldProductComponent } from './component/sale/dt-sold-product/dt-sold-product.component';
import { ItemDashboardComponent } from './component/dashboard/item-dashboard/item-dashboard.component';
import { ItemQuantityComponent } from './component/dashboard/item-quantity/item-quantity.component';
import { DirectiveModule } from '../shared/directives/directive.module';
import { ReportSaleDateComponent } from './modules/report/report-sale-date/report-sale-date.component';
import { ItemReportSaleDateComponent } from './component/report/item-report-sale-date/item-report-sale-date.component';
import { NgxMaskModule } from 'ngx-mask';
import { WharehouseSubsidiaryComponent } from './modules/conf/wharehouse-subsidiary/wharehouse-subsidiary.component';
import { ListWharehouseSubsidiaryComponent } from './component/config/list-wharehouse-subsidiary/list-wharehouse-subsidiary.component';
import { SearchWharehouseComponent } from './component/config/search-wharehouse/search-wharehouse.component';
import { ConfirmSaleComponent } from './component/sale/confirm-sale/confirm-sale.component';


const COMPONENT = [
  IntranetLayoutComponent,
  DasboardComponent,
  CategoryComponent,
  AddCategoryComponent,
  ListCategoryComponent,
  ProductComponent,
  MarkComponent,
  UnitComponent,
  AddUnitComponent,
  ListUnitComponent,
  AddMarkComponent,
  ListMarkComponent,
  AddProductComponent,
  ListProductComponent,
  SearchCategoryComponent,
  SearchMarkComponent,
  SearchUnitComponent,
  UserComponent,
  ListUserComponent,
  AddUserComponent,
  UserSubsidiaryComponent,
  ListUserSubsidiaryComponent,
  SearchSubsidiaryComponent,
  SecurityPolicyComponent,
  SeeSecurityPolicyComponent,
  ListAuthorityComponent,
  AddAuthorityComponent,
  AuthorityComponent,
  ProviderComponent,
  AddProviderComponent,
  ListProviderComponent,
  UserAuthorityComponent,
  ListUserAuthorityComponent,
  SearchAuthorityComponent,
  WharehouseComponent,
  AddWharehouseComponent,
  ListWharehouseComponent,
  EntryProductComponent,
  AddEntryProductComponent,
  DetailEntryProductComponent,
  OutSubWharehouseComponent,
  AddOutSubWarehouseComponent,
  DialogIncomeAmountComponent,
];

const COMPOMENTS_SALE = [
  SaleComponent,
  MakeSaleComponent,
  SoldProductComponent,
  SearchSoldProductComponent,
  ConfirmSaleComponent,
];

const COMPONETS_CUSTOMER = [
  CustomerComponent,
  ListCustomerComponent,
  AddCustomerComponent,
  DtSoldProductComponent,
];

const COMPONENTS_DASHBOARD = [
  ItemDashboardComponent,
  ItemQuantityComponent,
];

const COMPONENTS_REPORT = [
  ReportSaleDateComponent,
  ItemReportSaleDateComponent,
];

const COMPONENTS_CONF = [
  WharehouseSubsidiaryComponent,
  ListWharehouseSubsidiaryComponent,
  SearchWharehouseComponent,
];

@NgModule({
  declarations: [
    ...COMPONENT,
    ...COMPOMENTS_SALE,
    ...COMPONETS_CUSTOMER,
    ...COMPONENTS_DASHBOARD,
    ...COMPONENTS_REPORT,
    ...COMPONENTS_CONF,
  ],
  imports: [
    CommonModule,
    IntranetRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    SharedModule,
    DirectiveModule,
    NgxMaskModule.forChild(),
  ]
})
export class IntranetModule { }
