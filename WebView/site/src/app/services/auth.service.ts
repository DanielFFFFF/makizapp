// `makizapp/WebView/site/src/app/services/auth.service.ts`
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import {catchError, Observable, throwError} from 'rxjs';
import { tap } from 'rxjs/operators';
import {AppConfigService} from "../config/app.config.service";

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private loginUrl = '';

    // Dependency injection de HttpClient et AppConfigService
    constructor(private http: HttpClient, private config: AppConfigService){
        this.config.getConfig()
            .subscribe((config) => this.loginUrl = config.SERVER_PATH + '/login');
    }

  login(username: string, password: string): Observable<any> {
    return this.http.post<any>(this.loginUrl, { username, password },
      {
          headers: new HttpHeaders({'Content-Type': 'application/json'}),
          withCredentials: true, // Inclut les credentials dans les requêtes
      })
    .pipe(
      tap((response: any) => {
          if (response && response.token) {
              localStorage.setItem('token', response.token);
          } else {
              console.error('Token non reçu dans la réponse');
          }
      }),
      catchError((error) => {
          console.error('Erreur lors du login :', error);
          return throwError(error);
      }));

  }

  isLoggedIn(): boolean {
    return !!localStorage.getItem('token');
  }

  logout(): void {
    localStorage.removeItem('token');
  }

  getAuthHeaders(): HttpHeaders {
    const token = localStorage.getItem('token');
    return new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    });
  }
}
