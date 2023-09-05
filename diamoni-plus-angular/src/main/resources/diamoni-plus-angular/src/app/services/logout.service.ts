import {EventEmitter, Injectable} from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class LogoutService {

  logoutEmitter = new EventEmitter<void>();

  constructor() { }

  logout() {
    localStorage.removeItem('JSESSION_ID');
    localStorage.removeItem('ROLE');
    this.logoutEmitter.emit();
  }
}
