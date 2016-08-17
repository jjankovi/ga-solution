import { Component } from '@angular/core';
import {Observable} from 'rxjs/Observable';

import {LoginService} from "./login/login.service";

@Component({
  moduleId: module.id,
  selector: 'ib-app',
  templateUrl: 'app.component.html',
  styleUrls: ['app.component.css'],
  providers: [LoginService]
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
