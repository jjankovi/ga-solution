import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule }   from '@angular/forms';
import { HttpModule }   from '@angular/http';

import { AppComponent } from './app.component';
import {HomeComponent} from "./home/home.component";
import {routing, appRoutingProviders} from "./app.routing";

@NgModule({
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    routing
  ],
  declarations: [
    AppComponent,
    HomeComponent
  ],
  providers: [appRoutingProviders],
  exports: [ AppComponent ],
  bootstrap: [ AppComponent ]
})
export class AppModule {}
