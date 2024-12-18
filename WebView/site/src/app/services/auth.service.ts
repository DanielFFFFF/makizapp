// `makizapp/WebView/site/src/app/services/auth.service.ts`
import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {catchError, Observable, throwError} from 'rxjs';
import {tap} from 'rxjs/operators';
import {AppConfigService} from "../config/app.config.service";

@Injectable({
    providedIn: 'root',
})
export class AuthService {
    private loginUrl = '/api/auth/login';
    private registerUrl = '/api/auth/register'

    constructor(private http: HttpClient, private configService: AppConfigService) {
        this.configService.getConfig().subscribe((config) => {
            this.loginUrl = config.SERVER_PATH + this.loginUrl;
            this.registerUrl = config.SERVER_PATH + this.registerUrl;
        });
    }

    login(username: string, password: string): Observable<any> {
        return this.http.post<any>(this.loginUrl, {username, password})
            .pipe(
                tap((response: any) => {
                    if (response && response.token){
                        if (response.user_enabled == false)
                            throw new Error('User is disabled');
                        else localStorage.setItem('token', response.token);
                    }
                    else throw new Error('Token non reçu dans la réponse');
                }),
                catchError((error) => {
                    return throwError(error);
                }));

    }

    register(username: string, password: string): Observable<any> {
        return this.http.post<any>(this.registerUrl, {username, password})
            .pipe(
                tap((resp:any) => resp.message),
                catchError((error) => throwError(error))
            );
    }

    isLoggedIn(): boolean {
        return !!localStorage.getItem('token');
    }

    logout(): void {
        localStorage.removeItem('token');
    }

    getAuthHeaders(login = false, json = true) {
        const token = localStorage.getItem('token');
        const headers = new HttpHeaders();
        if (!json)
            headers.set('Content-Type', 'text/plain');

        if (!login) {
            headers.set('Authorization', `Bearer ${token}`);
        }
        return headers;
    }
}
