export interface ChangePasswordDTORequest{
    userName?:string;
    password?:string;
    newPassword?:string;
    confirmNewPassword?:string;
}