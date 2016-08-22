import {Injectable} from '@angular/core';

@Injectable()
export class LoggerService {

  info(message) {
    console.info(message);
  }

  debug(message) {
    console.debug(message);
  }

  warn(message) {
    console.warn(message);
  }

  error(message) {
    console.error(message);
  }


}
