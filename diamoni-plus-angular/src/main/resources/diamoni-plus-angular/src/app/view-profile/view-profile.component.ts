import {
  Component, OnDestroy, OnInit
} from '@angular/core';
import {
  catchError, EMPTY, of, Subject, switchMap, takeUntil, tap
} from "rxjs";
import {ProfileService} from "../services/profile.service";
import {ActivatedRoute, NavigationExtras, Router} from "@angular/router";
import {InfoService} from "../services/info.service";
import {AdminService} from "../services/admin.service";
import {Utils} from "../Utils";
import {
  GetUserProfileRespMsgType,
  RetrieveReviewsReqMsgType,
  ReviewResultType
} from "../model";
import {AuthService} from "../services/auth.service";
import {ReviewsService} from "../services/reviews.service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {DiscussionsService} from "../services/discussions.service";

@Component({
  selector: 'app-view-profile',
  templateUrl: './view-profile.component.html',
  styleUrls: ['./view-profile.component.css']
})
export class ViewProfileComponent implements OnInit, OnDestroy {
  loading = true;
  isAdmin = false;
  hasSubmittedReview = false;
  profile!: GetUserProfileRespMsgType;
  avatar!: string;
  isHostApproved!: boolean;
  isAuthenticated = false;
  isTenant = false;
  reviews: ReviewResultType[] = [];
  averageReviews = 0;
  page = 1;
  pageSize = 10;
  reviewForm!: FormGroup;
  reviewImages!: string[];
  profileFields: { label: string, value: string }[] = [];
  private destroy = new Subject<void>();
  private reviewsSubject = new Subject<RetrieveReviewsReqMsgType>();

  constructor(
    private profileService: ProfileService,
    private authService: AuthService,
    private adminService: AdminService,
    private reviewsService: ReviewsService,
    private discussionsService: DiscussionsService,
    private infoService: InfoService,
    private router: Router,
    private route: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.initReviewForm();
    this.loadReviews();

    const username = this.route.snapshot.queryParams['username'];

    this.reviewsSubject.pipe(takeUntil(this.destroy)).subscribe(params => {
      this.retrieveReviews(params.username, params.page, params.pageSize);
    });

    this.profileService.getUserProfile(username).pipe(
      takeUntil(this.destroy),
      tap(profile => {
        this.profileService.retrieveUserProfileImage(username).pipe(
          takeUntil(this.destroy)
        ).subscribe(res => {
          this.avatar = Utils.createDataUrl(res.avatar);
          this.loading = false;
        });
        this.handleHostApprovalInitialisation(username, profile);
      }),
      takeUntil(this.destroy)
    ).subscribe((profile: GetUserProfileRespMsgType) => {
      this.profile = profile;
      this.reviewsSubject.next({
        username: this.profile.username,
        page: this.page,
        pageSize: this.pageSize
      });
      this.initializeProfileFields();
    });
  }


  approveHost() {
    this.adminService.approveHostRole(this.profile.username)
      .pipe(takeUntil(this.destroy))
      .subscribe(() => {
        this.infoService.sendSuccess(`${this.profile.username} successfully approved!`);
        this.isHostApproved = true;
      })
  }

  paginate($event: any) {
    this.page = $event.page;
    this.pageSize = $event.pageCount;
    this.reviewsSubject.next({
      page: this.page,
      pageSize: this.pageSize,
      username: this.profile.username
    });
  }

  getProfileValue(key: string): any {
    switch(key) {
      case 'First Name': return this.profile.firstName;
      case 'Last Name': return this.profile.lastName;
      case 'Username': return this.profile.username;
      case 'Email': return this.profile.email;
      case 'Phone': return this.profile.phone;
      case 'Role': return this.profile.desiredRole;
      default: return null;
    }
  }

  moveToDiscussions() {
    this.discussionsService.createAndRetrieveDiscussion({
      hostUsername: this.profile.username
    }).pipe(takeUntil(this.destroy)).subscribe(res => {
      const queryParams: any = {
        discussionReference: res.discussionReference
      };

      const navigationExtras: NavigationExtras = {
        queryParams,
      };

      this.router.navigate(['/discussions'], navigationExtras);
    });

  }

  ngOnDestroy() {
    this.destroy.next();
    this.destroy.complete();
  }

  private handleHostApprovalInitialisation(username: string, profile: GetUserProfileRespMsgType) {
    this.authService.userInfo$.pipe(
      tap(userInfo => {
        this.isAuthenticated = true;
        this.isTenant = (userInfo.role === 'tenant');
      }),
      switchMap(userInfo => {
        this.isAdmin = (userInfo.role === 'admin');
        if (this.isAdmin && profile.desiredRole === 'host') {
          return this.adminService.retrieveHostApproval(username);
        }
        return of({approved: false});
      }),
      takeUntil(this.destroy)
    ).subscribe((res: any) => {
      this.isHostApproved = res.approved;
    });
  }

  private retrieveReviews(username: string | undefined, page: number, pageSize: number) {
    this.reviewsService.retrieveReviews({username, page, pageSize}).pipe(
      takeUntil(this.destroy),
      tap(res => {
        const authors = res.reviewResults.map(review => review.author);
        this.profileService.retrieveUserProfileImages(authors).pipe(takeUntil(this.destroy)).subscribe(images => {
          this.reviewImages = images;
        });
      })
    ).subscribe(res => {
      this.reviews = res.reviewResults;
      this.averageReviews = res.averageReviews;
    });
  }

  private initReviewForm() {
    this.reviewForm = this.fb.group({
      rating: [null, Validators.required],
      description: [null]
    });
  }

  submitReview() {
    this.reviewsService.submitReview({...this.reviewForm.value, hostUsername: this.profile.username});
    this.reviewsSubject.next({
      page: this.page, pageSize: this.pageSize, username: this.profile.username
    });
    this.reviewForm.reset();
  }

  private loadReviews() {
    this.reviewsSubject.pipe(
      switchMap(params => this.reviewsService.retrieveReviews(params).pipe(
        catchError(err => EMPTY)
      )),
      takeUntil(this.destroy)
    ).subscribe(({reviewResults, averageReviews}) => {
      this.reviews = reviewResults;
      this.averageReviews = averageReviews;
    });
  }

  getInitials(profile: GetUserProfileRespMsgType): string {
    if (!profile) return '';
    const initials = profile.firstName[0] + profile.lastName[0];
    return initials.toUpperCase();
  }

  private initializeProfileFields() {
    this.profileFields = [
      {label: 'First Name', value: this.profile.firstName},
      {label: 'Last Name', value: this.profile.lastName},
      {label: 'Username', value: this.profile.username},
      {label: 'Email', value: this.profile.email},
      {label: 'Phone', value: this.profile.phone},
      {label: 'Role', value: this.profile.desiredRole}
    ];
  }
}
