import {Component} from "@angular/core";
import {Store} from "@ngrx/store";
import {Observable, Subscription} from "rxjs";
import {Router} from "@angular/router";

import {LoggerService} from "../common/logger.service";
import {ClientService} from "../common/client.service";

@Component({
  moduleId: module.id,
  templateUrl: 'clients.component.html'
})
export class ClientsComponent {

  clients:Observable<any>;
  clientsSubscription:Subscription;

  constructor(private router:Router, private clientService:ClientService, private log:LoggerService, private store:Store<any>) {
    this.clients = store.select('searchedClients');
  }

  chooseClient(client) {
    this.clientService.selectClient(client);
  }

}
