import {Component, OnDestroy, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {MenuItem} from "primeng/api";
import {AuthService} from "../services/auth.service";
import {Subject, takeUntil} from "rxjs";
import {RetrieveUserInfoRespMsgType} from "../model";
import {Utils} from "../Utils";

@Component({
  selector: 'app-shell',
  templateUrl: './shell.component.html',
  styleUrls: ['./shell.component.css']
})
export class ShellComponent implements OnInit, OnDestroy {

  isLoggedIn = false;

  userInfo!: RetrieveUserInfoRespMsgType;

  fullNameAcronym!: string;

  items: MenuItem[] = [];

  avatarUrl!: string;

  private destroy = new Subject<void>();

  constructor(
    private router: Router,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.init();
    this.authService.updateShell.subscribe(() => this.init());
  }

  init() {
    this.authService.isAuthenticated$.pipe(takeUntil(this.destroy))
      .subscribe(isAuthenticated => this.isLoggedIn = isAuthenticated);

    this.authService.profileData$.pipe(takeUntil(this.destroy))
      .subscribe(profileData => {
        this.userInfo = profileData;
        this.fullNameAcronym = profileData.firstName.slice(0, 1) + profileData.lastName.slice(0, 1);
        if (this.userInfo?.avatar) {
          this.avatarUrl = Utils.createDataUrl(this.userInfo.avatar);
        }
        this.initItems();
      });
  }

  initItems() {
    this.items = [
      { label: 'Search Rental Spaces', icon: 'pi pi-fw pi-search', url: '/' }
    ];
    console.log(this.isLoggedIn);
    console.log(this.userInfo);
    if (this.isLoggedIn) {
      const role = this.userInfo?.role;
      if (role == 'host') {
        this.items.push({ label: 'My Rental Spaces', icon: 'pi pi-fw pi-building', url: '/my-rental-spaces' });
      } else if (role === 'tenant') {
        this.items.push({ label: 'My Bookings', icon: 'pi pi-fw pi-calendar', url: '/my-bookings' });
      } else if (role === 'admin') {
        this.items.push({ label: 'Manage Users', icon: 'pi pi-fw pi-users', url: '/manage-users' });
      }
      this.items.push({ label: 'Discussions', icon: 'pi pi-fw pi-comments', url: '/discussions' });
      this.items.push({ label: 'Logout', icon: 'pi pi-fw pi-sign-out', url: '/logout' });
    } else {
      this.items.push({ label: 'Discussions', icon: 'pi pi-fw pi-comments', url: '/discussions' });
      this.items.push({ label: 'Login', icon: 'pi pi-fw pi-sign-in', url: '/login' });
      this.items.push({ label: 'Register', icon: 'pi pi-fw pi-user-plus', url: '/register' });
    }
  }

  navigateToProfile() {
    this.router.navigate(["profile"]);
  }

  ngOnDestroy() {
    this.destroy.next();
    this.destroy.complete();
  }
}
