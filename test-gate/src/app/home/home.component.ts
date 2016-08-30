import {Component} from "@angular/core";
import {LoggerService} from "../common/logger.service";

@Component({
  moduleId: module.id,
  templateUrl: 'home.component.html'
})
export class HomeComponent {

  constructor(private log:LoggerService) {
    this.log.debug("HomeComponent initialized");
  }

  ngOnDestroy() {
    this.log.debug("HomeComponent is going to be destroyed");
  }

}
