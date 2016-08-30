import { Component } from '@angular/core';

import {LoggerService} from "./common/logger.service";

@Component({
  moduleId: module.id,
  selector: 'gate-app',
  templateUrl: 'app.component.html',
  providers: [LoggerService]
})
export class AppComponent {
}
