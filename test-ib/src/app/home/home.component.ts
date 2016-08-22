import {Component} from "@angular/core";
import {Router} from "@angular/router";
import {Observable} from "rxjs/Observable";

import {GAEvent} from "../common/gamodule/gaevent.model";
import {LoginService} from "../login/login.service";
import {GAModuleService} from "../common/gamodule/gamodule.service";
import {LoggerService} from "../common/logger.service";

@Component({
  moduleId: module.id,
  template:
    'Vitajte v IB - ste prihlaseny uzivatel: {{loggedUser | async | json}}',
  styleUrls: []
})
export class HomeComponent {

  loggedUserSubscription;
  cidSubscription;
  gaEventLogSubscription;

  loggedUser:Observable<any>;
  cid:string;

  constructor(private loginService:LoginService, private logger:LoggerService, private gaModuleService:GAModuleService, private router:Router) {
    this.loggedUser = loginService.getLoggedUser();

    this.loggedUserSubscription = this.loggedUser.subscribe(loggedUser => {
      if (loggedUser == null) {
        this.logger.debug("User is not logged, navigating to Login page...");
        this.router.navigate(['login']);
      } else {
        this.logger.debug("User is logged, obtaining CID from GAModule...");
        this.cidSubscription = gaModuleService.getCID(loggedUser.id).subscribe(result => {
          this.cid = result.text();
          this.logger.debug("CID successfully obtained: " + this.cid);
          this.gaEventLogSubscription = gaModuleService.logGAEvent(this._prepareGAEvent(this.cid)).subscribe(response => {
            logger.debug("GA event logged");
          });
        });
      }
    });
  }

  _prepareGAEvent(cid:string) {
    var gaEvent:GAEvent = new GAEvent();
    gaEvent.cid = cid;
    return gaEvent;
  }

  ngOnDestroy() {
    if (this.loggedUserSubscription) {
      this.loggedUserSubscription.unsubscribe();
      this.logger.debug("LoggedUserSubscription unsubscribed.");
    }
    if (this.cidSubscription) {
      this.cidSubscription.unsubscribe();
      this.logger.debug("CidSubscription unsubscribed.");
    }
    if (this.gaEventLogSubscription) {
      this.gaEventLogSubscription.unsubscribe();
      this.logger.debug("GaEventLogSubscription unsubscribed.");
    }
  }

}
