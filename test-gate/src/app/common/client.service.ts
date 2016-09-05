import {Injectable} from "@angular/core";
import { Store } from '@ngrx/store';
import {Router} from "@angular/router";
import {Http, Response, Headers, RequestOptions} from "@angular/http";

import {PROCESS_SEARCH_RESULT, CLEAR_SEARCH_RESULT} from '../reducers/searchedClients';
import {SELECT_CLIENT, DESELECT_CLIENT} from '../reducers/selectedClient';

@Injectable()
export class ClientService {

  constructor(private router:Router, private http:Http, private store:Store<any>) {}

  searchClient(searchPattern) {
    this.http.get('http://localhost:8080/clientdb/api?name=' + searchPattern)
      .map(this._extractClients)
      .subscribe(clients => {
        this.store.dispatch({type: PROCESS_SEARCH_RESULT, payload: clients});
        this.router.navigate(['clients']);
      });
  }

  selectClient(client) {
    let body = {eventType: 'BRANCH_ARRIVAL'};
    let headers = new Headers({ 'Content-Type': 'application/json' });
    let options = new RequestOptions({ headers: headers });

    this.http.post('http://localhost:8080/crm/api/event/' + client.id, body, options)
      .subscribe(eventPostResult => {
        this.store.dispatch({type: SELECT_CLIENT, payload: client});
        this.router.navigate(['clientDetail']);
      });
  }

  private _extractClients(res: Response) {
    let body = res.json();
    return body || [];
  }

}
