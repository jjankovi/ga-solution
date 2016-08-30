import { Routes, RouterModule }   from '@angular/router';
import {HomeComponent} from "./home/home.component";

const appRoutes: Routes = [
  { path: 'home', component: HomeComponent},
  { path: '**', redirectTo: 'home' }
];

export const appRoutingProviders: any[] = [

];

export const routing = RouterModule.forRoot(appRoutes);
