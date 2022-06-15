export interface SecurityPolicyDTO{
    id?:number;
    minpasswordlength?:number;
    maxpasswordlength?:number;
    maxnumberattempts?:number;
    maxidletime?:number;
    passwordchangefirstlogin?:string;
}