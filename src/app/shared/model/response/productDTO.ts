import { GenericDTO } from './GenericDTO';

export interface ProductDTO{
    id?:number;
    name?:string;
    summaryname?:string;
    isexpiratedate?:string;
    isrefrigeration?:string;
    isbatch?:string;
    isgeneric?:string;
    iskit?:string;
    ismedicine?:string;
    iscontrolled?:string;
    barcode?:string;
    category?:GenericDTO;
    mark?:GenericDTO;
    unit?:GenericDTO;
}