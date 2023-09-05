import {EventEmitter, Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import {LoginReqMsgType, LoginRespMsgType, RetrieveUserInfoRespMsgType} from '../model';
import {RegisterReqMsgType, RegisterRespMsgType} from '../model';
import {
  BehaviorSubject,
  catchError,
  first,
  Observable,
  ReplaySubject,
  tap
} from "rxjs";
import {environment} from "../../environments/environment";
import {ErrorService} from "./error.service";
import {InfoService} from "./info.service";
import {Router} from "@angular/router";
import {LogoutService} from "./logout.service";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  isAuthenticated$: Observable<boolean>;

  profileData$: Observable<RetrieveUserInfoRespMsgType>;

  updateShell = new EventEmitter<void>();

  private isAuthenticated = new BehaviorSubject<boolean>(false);

  private profileData = new ReplaySubject<RetrieveUserInfoRespMsgType>()

  constructor(
    private http: HttpClient,
    private errorService: ErrorService,
    private infoService: InfoService,
    private router: Router,
    private logoutService: LogoutService
  ) {
    logoutService.logoutEmitter.subscribe(() => {
      this.isAuthenticated.next(false);
    });

    this.isAuthenticated$ = this.isAuthenticated.asObservable();
    if (localStorage.getItem('JSESSION_ID')) {
      this.isAuthenticated.next(true);
    }

    this.profileData$ = this.profileData.asObservable();

    this.retrieveUserInfo()
      .pipe(
        first(),
        catchError((err: HttpErrorResponse) => this.infoService.sendInfo(err))
      )
      .subscribe(res => this.profileData.next(res));
  }

  login(payload: LoginReqMsgType) {
    this.http.post<LoginRespMsgType>(environment.auth.login, payload)
      .pipe(
        first(),
        catchError(err => this.errorService.sendError(err))
      ).subscribe(res => {
        localStorage.setItem('JSESSION_ID', res.jwt);
        this.isAuthenticated.next(true);
        this.router.navigate(['/']);
        this.updateShell.emit();
        this.infoService.sendSuccess('Successfully logged in!');
    });
  }

  register(payload: RegisterReqMsgType) {
    this.http.post<RegisterRespMsgType>(environment.auth.register, payload)
      .pipe(
        first(),
        catchError(err => this.errorService.sendError(err))
      )
      .subscribe( res => {
        localStorage.setItem('JSESSION_ID', res.jwt);
        this.isAuthenticated.next(true);
        this.router.navigate(['/']);
        this.updateShell.emit();
        this.infoService.sendSuccess('Successfully registered!');
      });
  }

  retrieveUserInfo(): Observable<RetrieveUserInfoRespMsgType> {
    return this.http.post<RetrieveUserInfoRespMsgType>(environment.auth.retrieveUserInfo, null)
      .pipe(
        tap((res) => {
          localStorage.setItem('ROLE', res.role);
          this.updateShell.emit();
        })
      );
  }
}
