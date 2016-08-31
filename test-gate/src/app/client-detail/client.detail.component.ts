import {Component} from "@angular/core";
import {Store} from "@ngrx/store";
import {Observable} from "rxjs";

@Component({
  moduleId: module.id,
  templateUrl: 'client.detail.component.html'
})
export class ClientDetailComponent {

  client:Observable<any>;

  constructor(private store:Store<any>) {
    this.client = store.select('selectedClient');
  }

}
