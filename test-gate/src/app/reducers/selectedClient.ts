// The "selectedClient" reducer handles currently selected client
import { ActionReducer, Action } from '@ngrx/store';

export const SELECT_CLIENT = 'SELECT_CLIENT';
export const DESELECT_CLIENT = 'DESELECT_CLIENT';

export const selectedClient:ActionReducer<any> = (state: any = null, action:Action) => {
  switch (action.type) {
    case SELECT_CLIENT:
      return action.payload;
    case DESELECT_CLIENT:
      return null;
    default:
      return state;
  }
};
