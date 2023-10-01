import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {ErrorService} from "./error.service";
import {
  CreateAndRetrieveDiscussionReqMsgType,
  CreateAndRetrieveDiscussionRespMsgType, MyBookingsRespMsgType,
  RetrieveDiscussionsRespMsgType, RetrieveMessagesReqMsgType, RetrieveMessagesRespMsgType, SaveMessageReqMsgType
} from "../model";
import {environment} from "../../environments/environment";
import {catchError} from "rxjs";
import {Utils} from "../Utils";

@Injectable({
  providedIn: 'root'
})
export class DiscussionsService {


  constructor(
    private http: HttpClient,
    private errorService: ErrorService
  ) {}

  createAndRetrieveDiscussion(payload: CreateAndRetrieveDiscussionReqMsgType) {
    return this.http.post<CreateAndRetrieveDiscussionRespMsgType>(environment.discussions.create, payload)
      .pipe(catchError(err => this.errorService.sendError(err)));
  }

  retrieveDiscussions() {
    return this.http.get<RetrieveDiscussionsRespMsgType>(environment.discussions.myDiscussions)
      .pipe(catchError(err => this.errorService.sendError(err)));
  }

  saveMessage(payload: SaveMessageReqMsgType) {
    return this.http.post(environment.discussions.messages, payload)
      .pipe(catchError(err => this.errorService.sendError(err)));
  }

  retrieveMessages(payload: RetrieveMessagesReqMsgType) {
    let params = new HttpParams();
    if (!Utils.isNullOrUndefined(payload.page)) {
      params = params.set('page', payload.page?.toString());
    }
    if (!Utils.isNullOrUndefined(payload.pageSize)) {
      params = params.set('pageSize', payload.pageSize?.toString());
    }
    if (!Utils.isNullOrUndefined(payload.discussionReference)) {
      params = params.set('discussionReference', payload.discussionReference);
    }
    return this.http.get<RetrieveMessagesRespMsgType>(environment.discussions.messages, {params})
      .pipe(catchError(err => this.errorService.sendError(err)));
  }

  deleteMessage(messageId: string) {
    const url = environment.discussions.deleteMessage.replace(':messageId', messageId);
    return this.http.delete(url).pipe(catchError(err => this.errorService.sendError(err)));
  }
}
