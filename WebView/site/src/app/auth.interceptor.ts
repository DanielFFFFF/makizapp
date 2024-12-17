import {inject} from '@angular/core';
import {HttpInterceptorFn,} from '@angular/common/http';
import {AuthService} from './services/auth.service';

export const AuthInterceptor: HttpInterceptorFn = (req, next) => {
    const auth = inject(AuthService);

    // Check if the request is a login request
    const isLogin = req.url.includes('/login');

    // Get the necessary headers for the request
    const authHeaders = auth.getAuthHeaders(isLogin);

    const clonedRequest = req.clone({
        headers: authHeaders,
    });

    return next(clonedRequest);
}
