import {NgModule} from "@angular/core";
import {BrowserModule} from "@angular/platform-browser";
import {FormsModule} from "@angular/forms";
import {HttpModule} from "@angular/http";
import {StoreModule} from "@ngrx/store";
import {loggedUser} from "./reducers/loggedUser";
import {routing, appRoutingProviders} from "./app.routing";
import {AppComponent} from "./app.component";
import {HomeComponent} from "./home/home.component";
import {LoginComponent} from "./login/login.component";

@NgModule({
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    StoreModule.provideStore({loggedUser: loggedUser}),
    routing
  ],
  declarations: [
    AppComponent,
    HomeComponent,
    LoginComponent
  ],
  providers: [appRoutingProviders],
  exports: [ AppComponent ],
  bootstrap: [ AppComponent ]
})
export class AppModule {}
