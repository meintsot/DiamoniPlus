import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {ErrorService} from "./error.service";
import {environment} from "../../environments/environment";
import {RetrieveReviewsReqMsgType, RetrieveReviewsRespMsgType, SubmitReviewReqMsgType} from "../model";
import {Utils} from "../Utils";
import {catchError, first} from "rxjs";
import {InfoService} from "./info.service";

@Injectable({
  providedIn: 'root'
})
export class ReviewsService {

  constructor(
    private http: HttpClient,
    private errorService: ErrorService,
    private infoService: InfoService
  ) {}

  retrieveReviews(payload: RetrieveReviewsReqMsgType) {
    let params = new HttpParams();
    if (!Utils.isNullOrUndefined(payload.page)) {
      params = params.set('page', payload.page?.toString());
    }
    if (!Utils.isNullOrUndefined(payload.pageSize)) {
      params = params.set('pageSize', payload.pageSize?.toString());
    }
    if (!Utils.isNullOrUndefined(payload.username)) {
      const username = payload.username as string;
      params = params.set('username', username);
    }
    if (!Utils.isNullOrUndefined(payload.rentalSpaceReference)) {
      const rentalSpaceReference = payload.rentalSpaceReference as string;
      params = params.set('rentalSpaceReference', rentalSpaceReference);
    }
    return this.http.get<RetrieveReviewsRespMsgType>(environment.reviews, {params})
      .pipe(
        catchError(err => this.errorService.sendError(err))
      );
  }

  submitReview(payload: SubmitReviewReqMsgType) {
    this.http.post(environment.reviews, payload)
      .pipe(catchError(err => this.errorService.sendError(err)))
      .subscribe(() => this.infoService.sendSuccess('Successfully submitted review!'));
  }
}
