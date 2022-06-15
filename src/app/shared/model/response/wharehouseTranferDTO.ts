import { WharehouseDTO } from './WharehouseDTO';

export interface WharehouseTranferDTO{
    distributions:WharehouseDTO[];
    subwarehouses:WharehouseDTO[];
}