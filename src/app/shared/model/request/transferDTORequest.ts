import { DtTransferDTORequest } from './dtTransferDTORequest';

export interface TransferDTORequest {
    idwharehouseorigin?: number;
    idwharehousedestination?: number;
    idsubsidiary?: number;
    detail?: DtTransferDTORequest[];
}