import { Routes } from '@angular/router';
import { AuthGuard } from 'src/app/shared/guard/auth.guard';
import { AccessGuard } from 'src/app/shared/guard/access.guard';

export const AUTHROUTES: Routes = [
    { path: '', canActivate: [AuthGuard], loadChildren: () => import('./../../auth/auth.module').then(m => m.AuthModule) },
    { path: 'intranet', canActivate: [AccessGuard], loadChildren: () => import('./../../intranet/intranet.module').then(m => m.IntranetModule) },
];
