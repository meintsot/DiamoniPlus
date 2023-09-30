import {throwError} from "rxjs";
import {HttpErrorResponse} from "@angular/common/http";
import {MessageService} from "primeng/api";
import {Injectable} from "@angular/core";

@Injectable({
  providedIn: 'root'
})
export class ErrorService {

  constructor(private messageService: MessageService) {}

  sendError(validationFault: HttpErrorResponse) { // Changed to any to catch all types
    this.messageService.add({
      severity: 'error',
      summary: validationFault.error.fault.string,
      detail: 'A Validation error has occurred.',
      life: 3000,
      closable: true
    });

    return throwError(validationFault);
  }
}
