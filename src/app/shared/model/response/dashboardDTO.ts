import { VSaleDateDTO } from './vSaleDateDTO';
import { DailySaleDTO } from './dailySaleDTO';
import { MonthSaleDTO } from './monthSaleDTO';

export interface DashboardDTO{
    quantityProduct?:number;
    quantityCategory?:number;
    quantityMark?:number;
    quantityProvider?:number;
    quantityUnit?:number;
    quantityCustomer?:number;
    lstSaleDate:VSaleDateDTO[];
    lstDailySale:DailySaleDTO[];
    lstMonthSale:MonthSaleDTO[];
}