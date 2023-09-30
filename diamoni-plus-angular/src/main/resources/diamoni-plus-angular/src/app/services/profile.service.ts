import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {ErrorService} from "./error.service";
import {
  GetUserProfileRespMsgType,
  RentalSpaceResultType, RetrieveRentalImageRespMsgType,
  RetrieveUserProfileImageRespMsgType,
  UpdateUserProfileReqMsgType
} from "../model";
import {environment} from "../../environments/environment";
import {catchError, first, forkJoin, map, Observable, of} from "rxjs";
import {Utils} from "../Utils";

@Injectable({
  providedIn: 'root'
})
export class ProfileService {

  constructor(
    private http: HttpClient,
    private errorService: ErrorService
  ) {}

  getUserProfile(username: string) {
    const url = environment.profile.getUserProfile.replace(":username", username);
    return this.http.get<GetUserProfileRespMsgType>(url).pipe(catchError(err => this.errorService.sendError(err)));
  }

  updateUserProfile(payload: UpdateUserProfileReqMsgType) {
    return this.http.put(environment.profile.common, payload)
      .pipe(
        first(),
        catchError(err => this.errorService.sendError(err))
      );
  }

  retrieveUserProfileImage(username: string) {
    const url = environment.profile.image.replace(":username", username);
    return this.http.get<RetrieveUserProfileImageRespMsgType>(url);
  }

  retrieveUserProfileImages(usernames: string[]): Observable<string[]> {
    const imageRequests: Observable<string>[] = usernames.map((username: string) => {
      return this.retrieveUserProfileImage(username).pipe(
        map((res: RetrieveUserProfileImageRespMsgType) => {
          return Utils.createDataUrl(res.avatar);
        }),
        catchError(err => of(''))
      );
    });

    return forkJoin([...imageRequests]);
  }
}
