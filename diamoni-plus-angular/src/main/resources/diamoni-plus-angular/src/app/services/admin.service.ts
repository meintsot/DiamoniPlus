import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {ErrorService} from "./error.service";
import {environment} from "../../environments/environment";
import {catchError} from "rxjs";
import {RetrieveHostApprovalRespMsgType, SearchUserProfilesReqMsgType, SearchUserProfilesRespMsgType} from "../model";
import {Utils} from "../Utils";

@Injectable({
  providedIn: 'root'
})
export class AdminService {

  constructor(
    private http: HttpClient,
    private errorService: ErrorService
  ) {}

  approveHostRole(username: string) {
    const url = environment.admin.host
      .replace(':username', username);

    return this.http.post(url, null)
      .pipe(
        catchError(err => this.errorService.sendError(err))
      );
  }

  retrieveHostApproval(username: string) {
    const url = environment.admin.host
      .replace(':username', username);

    return this.http.get<RetrieveHostApprovalRespMsgType>(url)
      .pipe(
        catchError(err => this.errorService.sendError(err))
      );
  }

  searchUserProfiles(payload: SearchUserProfilesReqMsgType) {

    let params = new HttpParams();
    if (!Utils.isNullOrUndefined(payload.page)) {
      params = params.set('page', payload.page?.toString());
    }
    if (!Utils.isNullOrUndefined(payload.pageSize)) {
      params = params.set('pageSize', payload.pageSize?.toString());
    }
    if (!Utils.isNullOrUndefined(payload.username)) {
      params = params.set('username', payload.username);
    }
    if (!Utils.isNullOrUndefined(payload.email)) {
      params = params.set('email', payload.email);
    }
    if (!Utils.isNullOrUndefined(payload.firstName)) {
      params = params.set('firstName', payload.firstName);
    }
    if (!Utils.isNullOrUndefined(payload.lastName)) {
      params = params.set('lastName', payload.lastName);
    }
    if (!Utils.isNullOrUndefined(payload.phone)) {
      params = params.set('phone', payload.phone);
    }
    if (!Utils.isNullOrUndefined(payload.roleType)) {
      params = params.set('roleType', payload.roleType);
    }
    if (!Utils.isNullOrUndefined(payload.isHostApproved)) {
      params = params.set('isHostApproved', payload.isHostApproved?.toString());
    }
    if (!Utils.isNullOrUndefined(payload.averageReviews)) {
      params = params.set('averageReviews', payload.averageReviews?.toString());
    }

    return this.http.get<SearchUserProfilesRespMsgType>(environment.admin.users, {params})
      .pipe(
        catchError(err => this.errorService.sendError(err))
      );
  }

  exportApplicationData() {

    return this.http.post(environment.admin.exportApplicationData, null)
      .pipe(
        catchError(err => this.errorService.sendError(err))
      );
  }
}
