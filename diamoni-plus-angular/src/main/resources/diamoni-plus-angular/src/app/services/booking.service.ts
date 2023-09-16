import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {ErrorService} from "./error.service";
import {
  ConfirmBookingReqMsgType,
  MyBookingsReqMsgType,
  MyBookingsRespMsgType
} from "../model";
import {environment} from "../../environments/environment";
import {catchError, first} from "rxjs";
import {Utils} from "../Utils";

@Injectable({
  providedIn: 'root'
})
export class BookingService {

  constructor(
    private http: HttpClient,
    private errorService: ErrorService
  ) {}

  confirmBooking(payload: ConfirmBookingReqMsgType) {
    return this.http.post(environment.booking.common, payload)
      .pipe(
        catchError(err => this.errorService.sendError(err))
      );
  }

  cancelBooking(bookingReference: string) {
    const url = environment.booking.delete
      .replace(':bookingReference', bookingReference);
    return this.http.delete<never>(url).pipe(
      catchError(err => this.errorService.sendError(err))
    );
  }

  myBookings(payload: MyBookingsReqMsgType) {
    let params = new HttpParams();
    if (!Utils.isNullOrUndefined(payload.page)) {
      params = params.set('page', payload.page?.toString());
    }
    if (!Utils.isNullOrUndefined(payload.pageSize)) {
      params = params.set('pageSize', payload.pageSize?.toString());
    }
    return this.http.get<MyBookingsRespMsgType>(environment.booking.common, {params})
      .pipe(
        first(),
        catchError(err => this.errorService.sendError(err))
      );
  }
}
