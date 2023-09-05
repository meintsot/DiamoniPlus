import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';

@Injectable({
  providedIn: 'root',
})
export class AuthGuard implements CanActivate {
  constructor(private router: Router) {}

  canActivate(): boolean {
    // Implement your logic here to check if user is authenticated
    if (localStorage.getItem('JSESSION_ID')) {
      return true;
    } else {
      this.router.navigate(['/login']);
      return false;
    }
  }
}
