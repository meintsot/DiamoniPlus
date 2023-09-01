import { Injectable } from '@angular/core';
import { CanActivate } from '@angular/router';
import {LogoutService} from "./logout.service"; // Update the path as needed

@Injectable({
  providedIn: 'root',
})
export class LogoutGuard implements CanActivate {
  constructor(private logoutService: LogoutService) {}

  canActivate(): boolean {
    this.logoutService.logout(); // Your logout logic here
    return false; // Prevent navigation to the logout URL
  }
}
