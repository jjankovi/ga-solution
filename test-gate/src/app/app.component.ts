import { Component } from '@angular/core';
import { Store } from '@ngrx/store';

import {LoggerService} from "./common/logger.service";
import {ClientService} from "./common/client.service";

@Component({
  moduleId: module.id,
  selector: 'gate-app',
  templateUrl: 'app.component.html',
  providers: [LoggerService, ClientService]
})
export class AppComponent {

}
