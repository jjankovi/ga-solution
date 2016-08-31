import {Component} from "@angular/core";

import {LoggerService} from "../common/logger.service";
import {ClientService} from "../common/client.service";

@Component({
  moduleId: module.id,
  templateUrl: 'home.component.html'
})
export class HomeComponent {

  searchPattern:string;

  constructor(private log:LoggerService, private clientService:ClientService) {}

  search() {
    this.log.debug("Entered searchPattern: " + this.searchPattern);
    if (!this.searchPattern) {
      this.log.debug("Search pattern is empty. Searching will not be initiated");
      return;
    }
    this.clientService.searchClient(this.searchPattern);
  }

}
