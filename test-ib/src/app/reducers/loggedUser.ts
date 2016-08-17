// The "loggedUser" reducer handles the currently logged user
import { ActionReducer, Action } from '@ngrx/store';

export const LOG_IN = 'LOG_IN';
export const LOG_OUT = 'LOG_OUT';

export const loggedUser:ActionReducer<any> = (state: any = null, action:Action) => {
  switch (action.type) {
    case LOG_IN:
      return action.payload;
    case LOG_OUT:
      return null;
    default:
      return state;
  }
};
