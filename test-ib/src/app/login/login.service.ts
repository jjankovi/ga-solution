import {Injectable} from '@angular/core';
import {Router} from '@angular/router';
import {Http, URLSearchParams, Response} from '@angular/http';
import { Store } from '@ngrx/store';

import {Login} from "./login.model";
import {LOG_IN, LOG_OUT} from '../reducers/loggedUser';

@Injectable()
export class LoginService {

  constructor(private http:Http, private router:Router, private store:Store<any>) {}

  login(login:Login) {
    let params = new URLSearchParams();
    params.set('login', login.username);
    params.set('password', login.password);

    this.http.get("http://localhost:8080/clientdb/api/login", {search:params})
      .subscribe(data => {
        if (data.text() != null && data.text() != "") {
          this.store.dispatch({type: LOG_IN, payload: data.json()});
        }
        this.router.navigate(['home']);
      });
  }

  logout() {
    this.store.dispatch({type: LOG_OUT});
    this.router.navigate(['login']);
  }

  getLoggedUser() {
    return this.store.select('loggedUser');
  }

}
