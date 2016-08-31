import { Routes, RouterModule }   from '@angular/router';

import {HomeComponent} from "./home/home.component";
import {ClientsComponent} from "./clients/clients.component";
import {ClientDetailComponent} from "./client-detail/client.detail.component";

const appRoutes: Routes = [
  { path: 'home', component: HomeComponent},
  { path: 'clients', component: ClientsComponent},
  { path: 'clientDetail', component: ClientDetailComponent},
  { path: '**', redirectTo: 'home' }
];

export const appRoutingProviders: any[] = [

];

export const routing = RouterModule.forRoot(appRoutes);
