import { Injectable } from '@angular/core';
import {
  HttpEvent,
  HttpInterceptor,
  HttpHandler,
  HttpRequest,
} from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    // Ajoutez ici des en-têtes personnalisés ou autres modifications
    const clonedRequest = req.clone({
      headers: req.headers.set('Content-Type', 'application/json'),
    });

    // Log pour le débogage
    console.log('Intercepted HTTP request:', clonedRequest);

    return next.handle(clonedRequest);
  }
}
