import {Injectable} from '@angular/core';
import {HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {catchError, Observable, throwError} from 'rxjs';
import {LogoutService} from "../services/logout.service";

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  private permitAllUrls = [
    '/api/auth/login',
    '/api/auth/register',
    // add more URLs here
  ];

  constructor(private logoutService: LogoutService) {}

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    const token = localStorage.getItem('JSESSION_ID');
    if (token && !this.permitAllUrls.includes(request.url)) {
      request = request.clone({
        setHeaders: {
          Authorization: `Bearer ${token}`
        }
      });
    }
    // solve circular import
    return next.handle(request).pipe(
      catchError((error: HttpErrorResponse) => {
        if (error.status === 401) {
          // Perform logout and other related actions
          this.logoutService.logout();
        }
        return throwError(error);
      })
    );
  }
}
