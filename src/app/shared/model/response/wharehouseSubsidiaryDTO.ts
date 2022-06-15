import { GenericDTO } from './GenericDTO';
import { WharehouseDTO } from './WharehouseDTO';

export interface WharehouseSubsidiaryDTO{
    id?:number;
    subsidiary?:GenericDTO;
    wharehouseDTO?:WharehouseDTO;
}