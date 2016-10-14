import {Component} from "@angular/core";
import {Router} from "@angular/router";
import {Observable} from "rxjs/Observable";

import {GAEvent} from "../common/gamodule/gaevent.model";
import {LoginService} from "../login/login.service";
import {GAModuleService} from "../common/gamodule/gamodule.service";
import {LoggerService} from "../common/logger.service";

@Component({
  template:
    'Vitajte v IB - ste prihlaseny uzivatel: {{loggedUser | async | json}}',
  styleUrls: []
})
export class HomeComponent {

  loggedUser:Observable<any>;
  cid:string;

  constructor(private loginService:LoginService, private logger:LoggerService, private gaModuleService:GAModuleService, private router:Router) {
    this.loggedUser = loginService.getLoggedUser();

    this.loggedUser.subscribe(loggedUser => {
      gaModuleService.getCID(loggedUser.id).subscribe(result => {
        this.cid = result.text();
        gaModuleService.logGAEvent(this._prepareGAEvent(this.cid)).subscribe(response => {
          logger.debug("GA event logged");
        });
      });
    });
  }

  _prepareGAEvent(cid:string) {
    var gaEvent:GAEvent = new GAEvent();
    gaEvent.cid = cid;
    return gaEvent;
  }

}
