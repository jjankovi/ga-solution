import { Component } from '@angular/core';
import {Observable} from 'rxjs/Observable';

import {LoginService} from "./login/login.service";
import {LoggerService} from "./common/logger.service";
import {GAModuleService} from "./common/gamodule/gamodule.service";

@Component({
  selector: 'ib-app',
  templateUrl: 'app.component.html',
  styleUrls: ['app.component.scss'],
  providers: [LoginService, LoggerService, GAModuleService]
})
export class AppComponent {

  loggedUser:Observable<any>;

  constructor(private loginService:LoginService) {
    this.loggedUser = loginService.getLoggedUser();
  }

  logout() {
    this.loginService.logout();
  }

}
