import {Injectable} from "@angular/core";
import {Http,Headers} from "@angular/http";
import {Observable} from "rxjs/Observable";
import {Store} from "@ngrx/store";

import {GAEvent} from "./gaevent.model";

@Injectable()
export class GAModuleService {

  constructor(private http:Http, private store:Store<any>) {}

  getCID(input:string):Observable<any> {
    return this.http.get("http://localhost:8080/gamodule/api/cid/" + input);
  }

  logGAEvent(gaEvent:GAEvent) {
    var headers = new Headers();
    headers.append("Content-Type", "application/json");
    return this.http.post("http://localhost:8080/gamodule/api/ga-event", JSON.stringify(gaEvent), {"headers":headers});
  }

}
