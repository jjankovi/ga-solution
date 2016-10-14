import { Routes, RouterModule }   from '@angular/router';

import {LoginComponent} from "./login/login.component";
import {HomeComponent} from "./home/home.component";
import {AuthGuard} from "./common/auth-guard.service";
import {LoginService} from "./login/login.service";

const appRoutes: Routes = [
  {
    path: 'home',
    component: HomeComponent,
    canActivate: [AuthGuard]
  },
  { path: 'login', component: LoginComponent },
  { path: '**', redirectTo: 'home' }
];

export const appRoutingProviders: any[] = [
  AuthGuard, LoginService
];

export const routing = RouterModule.forRoot(appRoutes);
