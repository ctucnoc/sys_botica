import { GenericDTO } from './GenericDTO';

export interface Subsidiary{
    id?:number;
    name?:string;
    address?:string;
    enterprise?:GenericDTO;
}