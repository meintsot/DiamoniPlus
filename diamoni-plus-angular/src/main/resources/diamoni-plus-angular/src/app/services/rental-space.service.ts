import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {
  CreateRentalSpaceReqMsgType,
  MyRentalSpacesReqMsgType,
  MyRentalSpacesRespMsgType,
  RentalSpaceResultType,
  RetrieveRentalImageRespMsgType,
  UploadRentalImageReqMsgType,
  UploadRentalImageRespMsgType
} from "../model";
import {catchError, first, forkJoin, map, Observable} from "rxjs";
import {ErrorService} from "./error.service";

@Injectable({
  providedIn: 'root'
})
export class RentalSpaceService {

  constructor(
    private http: HttpClient,
    private errorService: ErrorService
  ) {}

  retrieveRentalImage(rentalSpaceReference: string, binaryIdentification: string) {
    const url = environment.rentalSpace.retrieveRentalImage
      .replace(":rentalSpaceReference", rentalSpaceReference)
      .replace(":binaryIdentification", binaryIdentification);

    return this.http.get<RetrieveRentalImageRespMsgType>(url).pipe(
      first(),
      catchError(err => this.errorService.sendError(err))
    );
  }

  uploadRentalImage(payload: UploadRentalImageReqMsgType) {
    return this.http.post<UploadRentalImageRespMsgType>(environment.rentalSpace.uploadRentalImage, payload)
      .pipe(
        map((res: UploadRentalImageRespMsgType) => res.binaryIdentification),
        catchError(err => this.errorService.sendError(err)),
      );
  }

  deleteRentalImage(binaryIdentification: string) {
    const url = environment.rentalSpace.deleteRentalImage
      .replace(':binaryIdentification', binaryIdentification);
    return this.http.delete<never>(url).pipe(
      catchError(err => this.errorService.sendError(err))
    );
  }

  retrieveRentalImages(rentalSpaces: RentalSpaceResultType[]): Observable<string[]> {
    const imageRequests: Observable<string>[] = rentalSpaces.map((rentalSpace: RentalSpaceResultType) => {
      return this.retrieveRentalImage(rentalSpace.rentalSpaceReference, rentalSpace.rentalImageIdentification).pipe(
        map((res: RetrieveRentalImageRespMsgType) => {
          const rentalImage = res.rentalImage;
          return 'data' + rentalImage.mime + ';base64,' + rentalImage.data;
        })
      );
    });

    return forkJoin([...imageRequests]);
  }

  myRentalSpaces(payload: MyRentalSpacesReqMsgType) {
    return this.http.get<MyRentalSpacesRespMsgType>(environment.rentalSpace.myRentalSpaces)
      .pipe(
        first(),
        catchError(err => this.errorService.sendError(err))
      );
  }

  createRentalSpace(payload: CreateRentalSpaceReqMsgType) {
    return this.http.post(environment.rentalSpace.createRentalSpace, payload)
      .pipe(
        catchError(err => this.errorService.sendError(err))
      );
  }
}
