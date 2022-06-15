import { DtSaleDTORequest } from './dtSaleDTORequest';

export interface SaleDTORequest {
    idCustomer?: number;
    idProofpayment?: string;
    details?: DtSaleDTORequest[];
}

export interface SaleParam{
    idcustomer?:number;
    idproofpayment?:number;
    dateFrom?:string;
    dateTo?:string;
}