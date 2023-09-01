import { Injectable } from '@angular/core';
import {MessageService} from "primeng/api";
import {HttpErrorResponse} from "@angular/common/http";
import {Observable, throwError} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class InfoService {

  constructor(private messageService: MessageService) {}

  sendSuccess(message: string) {
    this.messageService.add({
      severity: 'success',
      summary: 'Success!',
      detail: message,
      life: 3000,
      closable: true
    });
  }

  sendInfo(error: HttpErrorResponse): Observable<never>  {
    this.messageService.add({
      severity: 'info',
      summary: 'Info',
      detail: error.message,
      life: 3000,
      closable: true
    });

    return throwError(error);
  }
}
