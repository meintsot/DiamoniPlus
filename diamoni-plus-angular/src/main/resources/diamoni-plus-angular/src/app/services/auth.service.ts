import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {
  LoginReqMsgType,
  LoginRespMsgType,
  RegisterReqMsgType,
  RegisterRespMsgType,
  RetrieveUserInfoRespMsgType
} from '../model';
import {BehaviorSubject, catchError, first, Observable, ReplaySubject, switchMap, tap} from "rxjs";
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
  userInfo$: Observable<RetrieveUserInfoRespMsgType>;

  private isAuthenticated = new BehaviorSubject<boolean>(false);
  private userInfoSubject = new ReplaySubject<RetrieveUserInfoRespMsgType>(1);

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
    this.userInfo$ = this.userInfoSubject.asObservable();
    if (localStorage.getItem('JSESSION_ID')) {
      this.isAuthenticated.next(true);
    }
  }

  login(payload: LoginReqMsgType) {
    this.http.post<LoginRespMsgType>(environment.auth.login, payload)
      .pipe(
        first(),
        catchError(err => this.errorService.sendError(err))
      ).subscribe(res => {
        localStorage.setItem('JSESSION_ID', res.jwt);
        this.router.navigate(['/']);
        this.isAuthenticated.next(true);
        this.infoService.sendSuccess('Successfully logged in!');
    });
  }

  register(payload: RegisterReqMsgType) {
    this.http.post<RegisterRespMsgType>(environment.auth.register, payload)
      .pipe(
        first(),
        catchError(err => this.errorService.sendError(err))
      )
      .subscribe(res => {
        localStorage.setItem('JSESSION_ID', res.jwt);
        this.isAuthenticated.next(true);
        this.router.navigate(['/']);
        this.infoService.sendSuccess('Successfully registered!');
      });
  }

  retrieveUserInfo(): Observable<RetrieveUserInfoRespMsgType> {
    return this.http.post<RetrieveUserInfoRespMsgType>(environment.auth.retrieveUserInfo, null)
      .pipe(
        switchMap(res => {
          this.userInfoSubject.next(res);
          return this.userInfo$;
        }),
        tap((res) => {
          localStorage.setItem('ROLE', res.role);
        }),
        catchError(err => {
          this.isAuthenticated.next(false);
          localStorage.removeItem('JSESSION_ID');
          localStorage.removeItem('ROLE');
          return this.infoService.sendInfo(err);
        })
      );
  }
}
