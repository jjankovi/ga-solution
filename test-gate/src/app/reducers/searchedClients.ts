// The "searchedClients" reducer handles currently searched clients
import { ActionReducer, Action } from '@ngrx/store';

export const PROCESS_SEARCH_RESULT = 'PROCESS_SEARCH_RESULT';
export const CLEAR_SEARCH_RESULT = 'CLEAR_SEARCH_RESULT';

export const searchedClients:ActionReducer<any> = (state: any = null, action:Action) => {
  switch (action.type) {
    case PROCESS_SEARCH_RESULT:
      return action.payload;
    case CLEAR_SEARCH_RESULT:
      return null;
    default:
      return state;
  }
};
