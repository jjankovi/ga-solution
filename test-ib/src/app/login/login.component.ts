import { Component } from '@angular/core';

import {Login} from "./login.model";
import {LoginService} from "./login.service";

@Component({
  templateUrl: 'login.component.html',
  providers: [LoginService]
})
export class LoginComponent {

  model:Login = new Login();

  constructor(private loginService: LoginService) {}

  login() {
    this.loginService.login(this.model);
  }

}
