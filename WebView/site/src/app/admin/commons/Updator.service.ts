import {Injectable} from '@angular/core';
import {Subject} from 'rxjs';

/**
 * @class UpdatorService
 *
 * Service that provides a mechanism to refresh data.
 */
@Injectable({
  providedIn: 'root'
})
export class UpdatorService {

  /**
   * @property _refreshNeeded$
   * Subject that emits events when a refresh is needed.
   */
  private _refreshNeeded$ = new Subject<void>();

  /**
   * @method refreshNeeded$
   * Observable that can be subscribed to in order to react to refresh events.
   */
  get refreshNeeded$() {
    return this._refreshNeeded$.asObservable();
  }

  /**
   * @method refresh
   * Triggers a refresh event.
   */
  refresh() {
    this._refreshNeeded$.next();
  }
}
