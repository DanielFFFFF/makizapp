import { Injectable } from '@angular/core';
import {Observable, of} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class AppConfigService {
  private configUrl = 'assets/app.config.json';

  getConfig(): Observable<any> {
    return of({ SERVER_PATH: ''})
  }
}
