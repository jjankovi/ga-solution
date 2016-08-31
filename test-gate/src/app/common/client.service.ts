import {Injectable} from "@angular/core";
import { Store } from '@ngrx/store';
import {Router} from "@angular/router";

import {PROCESS_SEARCH_RESULT, CLEAR_SEARCH_RESULT} from '../reducers/searchedClients';
import {SELECT_CLIENT, DESELECT_CLIENT} from '../reducers/selectedClient';

@Injectable()
export class ClientService {

  constructor(private router:Router, private store:Store<any>) {}

  searchClient(searchPattern) {
    this.store.dispatch(
      {
        type: PROCESS_SEARCH_RESULT,
        payload: [{firstName:searchPattern, lastName:searchPattern}]
      });
    this.router.navigate(['clients']);
  }

  selectClient(client) {
    this.store.dispatch(
      {
        type: SELECT_CLIENT,
        payload: client
      });
    this.router.navigate(['clientDetail']);
  }

}
