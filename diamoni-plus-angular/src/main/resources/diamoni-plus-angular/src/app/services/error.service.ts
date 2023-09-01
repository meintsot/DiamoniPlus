import {throwError} from "rxjs";
import {HttpErrorResponse} from "@angular/common/http";
import {MessageService} from "primeng/api";
import {Injectable} from "@angular/core";

@Injectable({
  providedIn: 'root'
})
export class ErrorService {

  constructor(private messageService: MessageService) {}

  sendError(validationFault: any) { // Changed to any to catch all types
    if (validationFault instanceof HttpErrorResponse) {
      this.messageService.add({
        severity: 'error',
        summary: validationFault.message,
        detail: 'An HTTP error occurred.',
        life: 3000,
        closable: true
      });
    } else if (validationFault && validationFault.fault) { // Check if fault field exists
      this.messageService.add({
        severity: 'error',
        summary: validationFault.fault,
        detail: 'An error occurred.',
        life: 3000,
        closable: true,
        key: 'error-toast',
      });
    }

    return throwError(validationFault);
  }
}
