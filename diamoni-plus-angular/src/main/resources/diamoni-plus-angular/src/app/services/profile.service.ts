import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {ErrorService} from "./error.service";
import {GetUserProfileRespMsgType, UpdateUserProfileReqMsgType} from "../model";
import {environment} from "../../environments/environment";
import {catchError, first} from "rxjs";

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
}
