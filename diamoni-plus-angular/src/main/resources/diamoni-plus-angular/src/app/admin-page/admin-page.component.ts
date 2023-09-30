import {Component, OnDestroy, OnInit} from '@angular/core';
import {AdminService} from "../services/admin.service";
import {NavigationExtras, Router} from "@angular/router";
import {catchError, EMPTY, Subject, switchMap, takeUntil} from "rxjs";
import {SearchUserProfilesReqMsgType, UserProfileResult} from "../model";
import {FormBuilder, FormGroup} from "@angular/forms";
import {TablePageEvent} from "primeng/table";
import {ProfileService} from "../services/profile.service";

@Component({
  selector: 'app-admin-page',
  templateUrl: './admin-page.component.html',
  styleUrls: ['./admin-page.component.css']
})
export class AdminPageComponent implements OnInit, OnDestroy {

  isLoading = true;

  page = 1;

  pageSize = 10;

  filterForm!: FormGroup;

  users: UserProfileResult[]  = [];
  avatars: string[] = [];

  totalUsers = 0;

  private loadUsersLazy = new Subject<SearchUserProfilesReqMsgType>();

  private destroy = new Subject<void>();

  constructor(
    private adminService: AdminService,
    private profileService: ProfileService,
    private router: Router,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.initFilterForm();

    this.loadUsersLazy.pipe(
      switchMap(params => this.adminService.searchUserProfiles(params)),
      catchError(err => EMPTY),
      takeUntil(this.destroy)
    ).subscribe(res => {
      this.users = res.userProfileResults;
      this.totalUsers = res.totalResults;
      this.loadAvatars();
    });

    this.onLoadUsersLazy();
  }

  onPageChange($event: TablePageEvent) {
    this.page = $event.first;
    this.pageSize = $event.rows;

    this.onLoadUsersLazy();
  }

  exportData() {
    this.adminService.exportApplicationData().subscribe(data => {
      const blob = new Blob([JSON.stringify(data)], { type: 'application/json' });
      const url= window.URL.createObjectURL(blob);
      var a = document.createElement('a');
      a.href = url;
      a.download = 'exported-data.json';
      a.click();
      window.URL.revokeObjectURL(url);
    });
  }

  ngOnDestroy(): void {
    this.destroy.next();
    this.destroy.complete();
  }

  private initFilterForm() {
    this.filterForm = this.fb.group({
      username: [null],
      email: [null],
      firstName: [null],
      lastName: [null],
      phone: [null],
      roleType: [null],
      isHostApproved: [null],
      averageReviews: [null],
    });

    this.filterForm.valueChanges.subscribe(newValue => {
      this.loadUsersLazy.next(
        {
          page: this.page,
          pageSize: this.pageSize,
          ...newValue
        }
      );
    })
  }

  private onLoadUsersLazy() {
    this.loadUsersLazy.next(
      {
        page: this.page,
        pageSize: this.pageSize,
        ...this.filterForm.getRawValue()
      }
    );
  }

  private loadAvatars() {
    this.profileService.retrieveUserProfileImages(this.users.map(user => user.username))
      .pipe(takeUntil(this.destroy)).subscribe(images => {
        this.avatars = images;
        this.isLoading = false;
      });
  }

  viewProfile(username: string) {
    const queryParams: any = {
      username
    };

    const navigationExtras: NavigationExtras = {
      queryParams,
    };

    this.router.navigate(['/view-profile'], navigationExtras);
  }
}
