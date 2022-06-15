export interface SecurityPolicyDTORequest{
    minpasswordlength?:number;
    maxpasswordlength?:number;
    maxnumberattempts?:number;
    maxidletime?:number;
    passwordchangefirstlogin?:string;
}