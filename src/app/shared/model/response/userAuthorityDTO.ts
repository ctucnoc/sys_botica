import { GenericDTO } from './GenericDTO';

export interface UserAuthorityDTO{
    id?:number;
    user?:GenericDTO;
    authority?:GenericDTO;
}