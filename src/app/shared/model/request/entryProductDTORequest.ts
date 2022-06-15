import { DtEntryProductDTORequest } from './dtEntryProductDTORequest';

export interface EntryProductDTORequest{
    idProvider?:number;
    idWharehouse?:number;
    idEntryDocument?:number;
    details?:DtEntryProductDTORequest[];
}