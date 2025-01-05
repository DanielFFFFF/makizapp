import {HttpInterceptorFn,} from '@angular/common/http';

export const AuthInterceptor: HttpInterceptorFn = (req, next) => {
    const token = localStorage.getItem('token');

    if (!token)
        return next(req);

    let headers = req.headers;
    // Check if the request is a login request
    if (!req.url.includes('login'))
        headers = headers.set('Authorization', `Bearer ${token}`);

    const clonedRequest = req.clone({headers});
    return next(clonedRequest);
}
