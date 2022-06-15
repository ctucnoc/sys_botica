import { VSaleDateDTO } from './vSaleDateDTO';

export interface DashboardDTO{
    quantityProduct?:number;
    quantityCategory?:number;
    quantityMark?:number;
    quantityProvider?:number;
    quantityUnit?:number;
    quantityCustomer?:number;
    lstSaleDate:VSaleDateDTO[];
}