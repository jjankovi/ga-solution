import { Component } from '@angular/core';
import {Router} from '@angular/router';
import {Observable} from 'rxjs/Observable';

import {LoginService} from "../login/login.service";

@Component({
  moduleId: module.id,
  template:
    'Vitajte v IB - ste prihlaseny uzivatel: {{loggedUser | async | json}}',
  styleUrls: []
})
export class HomeComponent {

  loggedUser:Observable<any>;

  constructor(private loginService:LoginService, private router:Router) {
    this.loggedUser = loginService.getLoggedUser();
    this.loggedUser.subscribe(loggedUser => {
      if (loggedUser == null) {
        this.router.navigate(['login']);
      }
    });
  }
}
