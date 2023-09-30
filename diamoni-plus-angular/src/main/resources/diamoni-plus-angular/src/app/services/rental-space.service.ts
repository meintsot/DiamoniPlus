import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {
  CreateRentalSpaceReqMsgType,
  MyRentalSpacesReqMsgType,
  MyRentalSpacesRespMsgType,
  RentalSpaceResultType,
  RetrieveRentalImageRespMsgType,
  RetrieveRentalSpaceDetailsRespMsgType,
  SearchRentalSpacesReqMsgType,
  SearchRentalSpacesRespMsgType, UpdateRentalSpaceDetailsReqMsgType,
  UploadRentalImageReqMsgType,
  UploadRentalImageRespMsgType
} from "../model";
import {catchError, first, forkJoin, map, Observable} from "rxjs";
import {ErrorService} from "./error.service";
import {Utils} from "../Utils";

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
          return Utils.createDataUrl(res.rentalImage);
        })
      );
    });

    return forkJoin([...imageRequests]);
  }

  retrieveRentalImagesFromRentalSpaceDetails(rentalSpaceDetails: RetrieveRentalSpaceDetailsRespMsgType): Observable<string[]> {
    const imageRequests: Observable<string>[] = rentalSpaceDetails.rentalImageIdentifications.map((imageIdentification: string) => {
      return this.retrieveRentalImage(rentalSpaceDetails.rentalSpaceReference, imageIdentification).pipe(
        map((res: RetrieveRentalImageRespMsgType) => {
          return Utils.createDataUrl(res.rentalImage);
        })
      );
    });

    return forkJoin([...imageRequests]);
  }

  myRentalSpaces(payload: MyRentalSpacesReqMsgType) {
    let params = new HttpParams();
    if (!Utils.isNullOrUndefined(payload.page)) {
      params = params.set('page', payload.page?.toString());
    }
    if (!Utils.isNullOrUndefined(payload.pageSize)) {
      params = params.set('pageSize', payload.pageSize?.toString());
    }
    return this.http.get<MyRentalSpacesRespMsgType>(environment.rentalSpace.myRentalSpaces, {params})
      .pipe(
        first(),
        catchError(err => this.errorService.sendError(err))
      );
  }

  searchRentalSpaces(payload: SearchRentalSpacesReqMsgType) {
    let params = new HttpParams();
    if (!Utils.isStringBlank(payload.location?.country)) {
      params = params.set('country', payload.location?.country);
    }
    if (!Utils.isStringBlank(payload.location?.city)) {
      params = params.set('city', payload.location?.city);
    }
    if (!Utils.isStringBlank(payload.location?.address)) {
      params = params.set('address', payload.location?.address);
    }
    if (!Utils.isStringBlank(payload.location?.suburb)) {
      params = params.set('suburb', payload.location?.suburb);
    }
    if (!Utils.isStringBlank(payload.location?.postcode)) {
      params = params.set('postcode', payload.location?.postcode);
    }
    if (!Utils.isStringBlank(payload.startDate)) {
      params = params.set('startDate', payload.startDate);
    }
    if (!Utils.isStringBlank(payload.endDate)) {
      params = params.set('endDate', payload.endDate);
    }
    if (!Utils.isNullOrUndefined(payload.maxNumOfPeople)) {
      params = params.set('maxNumOfPeople', payload.maxNumOfPeople?.toString());
    }
    if (!Utils.isNullOrUndefined(payload.page)) {
      params = params.set('page', payload.page?.toString());
    }
    if (!Utils.isNullOrUndefined(payload.pageSize)) {
      params = params.set('pageSize', payload.pageSize?.toString());
    }
    if (!Utils.isStringBlank(payload.roomType)) {
      params = params.set('roomType', payload.roomType);
    }
    if (!Utils.isNullOrUndefined(payload.maximumCost)) {
      params = params.set('maximumCost', payload.maximumCost?.toString());
    }
    if (!Utils.isNullOrUndefined(payload.amenities?.wirelessInternet)) {
      params = params.set('wirelessInternet', payload.amenities?.wirelessInternet?.toString());
    }
    if (!Utils.isNullOrUndefined(payload.amenities?.cooling)) {
      params = params.set('cooling', payload.amenities?.cooling?.toString());
    }
    if (!Utils.isNullOrUndefined(payload.amenities?.heating)) {
      params = params.set('heating', payload.amenities?.heating?.toString());
    }
    if (!Utils.isNullOrUndefined(payload.amenities?.kitchen)) {
      params = params.set('kitchen', payload.amenities?.kitchen?.toString());
    }
    if (!Utils.isNullOrUndefined(payload.amenities?.television)) {
      params = params.set('television', payload.amenities?.television?.toString());
    }
    if (!Utils.isNullOrUndefined(payload.amenities?.parkingSpace)) {
      params = params.set('parkingSpace', payload.amenities?.parkingSpace?.toString());
    }
    if (!Utils.isNullOrUndefined(payload.amenities?.elevator)) {
      params = params.set('elevator', payload.amenities?.elevator?.toString());
    }
    return this.http.get<SearchRentalSpacesRespMsgType>(environment.rentalSpace.common, {params})
      .pipe(
        catchError(err => this.errorService.sendError(err))
      );
  }

  createRentalSpace(payload: CreateRentalSpaceReqMsgType) {
    return this.http.post(environment.rentalSpace.common, payload)
      .pipe(
        catchError(err => this.errorService.sendError(err))
      );
  }

  updateRentalSpaceDetails(payload: UpdateRentalSpaceDetailsReqMsgType) {
    return this.http.put(environment.rentalSpace.common, payload)
      .pipe(
        catchError(err => this.errorService.sendError(err))
      );
  }

  retrieveRentalSpaceDetails(rentalSpaceReference: string) {
    const url = environment.rentalSpace.rentalSpaceDetails
      .replace(":rentalSpaceReference", rentalSpaceReference);
    return this.http.get<RetrieveRentalSpaceDetailsRespMsgType>(url)
      .pipe(
        first(),
        catchError(err => this.errorService.sendError(err))
      );
  }
}
