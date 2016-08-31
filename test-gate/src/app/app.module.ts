import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule }   from '@angular/forms';
import { HttpModule }   from '@angular/http';
import { Store, StoreModule } from '@ngrx/store';

import { AppComponent } from './app.component';
import {routing, appRoutingProviders} from "./app.routing";
import {searchedClients} from "./reducers/searchedClients";
import {selectedClient} from "./reducers/selectedClient";
import {HomeComponent} from "./home/home.component";
import {ClientsComponent} from "./clients/clients.component";
import {ClientDetailComponent} from "./client-detail/client.detail.component";

@NgModule({
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    StoreModule.provideStore({
      searchedClients: searchedClients,
      selectedClient: selectedClient
    }),
    routing
  ],
  declarations: [
    AppComponent,
    HomeComponent,
    ClientsComponent,
    ClientDetailComponent
  ],
  providers: [appRoutingProviders],
  exports: [ AppComponent ],
  bootstrap: [ AppComponent ]
})
export class AppModule {}
