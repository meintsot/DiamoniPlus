import {Component, OnDestroy, OnInit} from '@angular/core';
import {RentalSpaceService} from "../services/rental-space.service";
import {
  RentalSpaceDateRangeType,
  RetrieveRentalSpaceDetailsRespMsgType,
  RetrieveReviewsReqMsgType, RetrieveUserInfoRespMsgType,
  ReviewResultType
} from "../model";
import {ActivatedRoute, Router} from "@angular/router";
import {catchError, EMPTY, Subject, switchMap, takeUntil} from "rxjs";
import * as Leaflet from "leaflet";
import {PaginatorState} from "primeng/paginator";
import {ReviewsService} from "../services/reviews.service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Utils} from "../Utils";
import {AuthService} from "../services/auth.service";
import {ListboxChangeEvent} from "primeng/listbox";
import {BookingService} from "../services/booking.service";
import {InfoService} from "../services/info.service";
import {Util} from "leaflet";

@Component({
  selector: 'app-rental-space-details',
  templateUrl: './rental-space-details.component.html',
  styleUrls: ['./rental-space-details.component.css']
})
export class RentalSpaceDetailsComponent implements OnInit, OnDestroy {

  rentalSpaceDetails!: RetrieveRentalSpaceDetailsRespMsgType;
  locationMapOptions!: Leaflet.MapOptions;
  transportationMapOptions!: Leaflet.MapOptions;
  images!: string[];
  isLoading = true;
  hasSubmittedReview = false;
  reviews: ReviewResultType[] = [];
  averageReviews!: number;
  reviewForm!: FormGroup;
  page  = 1;
  pageSize = 10;
  userInfo!: RetrieveUserInfoRespMsgType;
  isLoggedIn = false;
  availableRentPeriods: { range: string, index: number }[] = [];
  selectedRentPeriod!: RentalSpaceDateRangeType;
  showDialog = false;
  bookedDateRange!: string;

  private reviewsSubject = new Subject<RetrieveReviewsReqMsgType>();
  private destroy = new Subject<void>();

  constructor(
    private authService: AuthService,
    private rentalSpaceService: RentalSpaceService,
    private bookingService: BookingService,
    private reviewsService: ReviewsService,
    private infoService: InfoService,
    private router: Router,
    private route: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    const rentalSpaceReference = this.route.snapshot.queryParams['rentalSpaceReference'];
    this.initReviewForm();
    this.rentalSpaceService.retrieveRentalSpaceDetails(rentalSpaceReference)
      .pipe(takeUntil(this.destroy))
      .subscribe(res => {
        if (!Utils.isNullOrUndefined(res.bookedDateRange)) {
          this.bookedDateRange = `${res.bookedDateRange.startDate} - ${res.bookedDateRange.endDate}`;
        }
        this.createDateRange(res.availableRentPeriods);
        this.rentalSpaceDetails = res;
        this.loadLogin();
        this.loadUserInfo();
        this.initLocationMap();
        this.initTransportationMap();
        this.loadImages();
        this.loadReviews();
        this.isLoading = false;
      });
  }

  isRented(): boolean {
    return !Utils.isNullOrUndefined(this.rentalSpaceDetails.bookingReference)
  }

  selectDateRange($event: ListboxChangeEvent) {
    this.selectedRentPeriod = this.rentalSpaceDetails.availableRentPeriods[$event.value.index];
  }

  paginate($event: PaginatorState) {

  }

  submitReview() {
    this.reviewsService.submitReview({...this.reviewForm.value, bookingReference: this.rentalSpaceDetails.bookingReference});
    this.reviewsSubject.next({
      page: this.page, pageSize: this.pageSize, rentalSpaceReference: this.rentalSpaceDetails.rentalSpaceReference
    });
  }

  submitBooking() {
    this.bookingService.confirmBooking({
      rentalSpaceReference: this.rentalSpaceDetails.rentalSpaceReference,
      bookingRange: this.selectedRentPeriod
    }).pipe(takeUntil(this.destroy)).subscribe(() => {
      this.router.navigate(['my-bookings']);
      this.infoService.sendSuccess('Rental Space booked!');
    });
  }

  isTenant() {
    console.log(this.userInfo.role);
    return this.userInfo.role === 'tenant';
  }

  isAvailableForBooking() {
    return this.rentalSpaceDetails.availableRentPeriods.length !== 0;
  }

  isBooked() {
    return  !Utils.isNullOrUndefined(this.rentalSpaceDetails.bookingReference);
  }

  cancelBooking() {
    this.bookingService.cancelBooking(this.rentalSpaceDetails.bookingReference)
      .pipe(takeUntil(this.destroy)).subscribe(() => {
        this.router.navigate(['my-bookings']);
        this.infoService.sendSuccess('Successfully cancelled booking!');
    });
  }

  ngOnDestroy() {
    this.destroy.next();
    this.destroy.complete();
  }

  private createDateRange(dateRanges: RentalSpaceDateRangeType[]) {
    this.availableRentPeriods = dateRanges.map((dateRange, index) =>
      ({range: `${dateRange.startDate} - ${dateRange.endDate}`, index}))
  }

  private loadUserInfo() {
    this.authService.userInfo$.pipe(takeUntil(this.destroy)).subscribe(userInfo => {
      this.userInfo = userInfo;
    });
  }

  private loadLogin() {
    this.authService.isAuthenticated$.pipe(takeUntil(this.destroy)).subscribe(res => this.isLoggedIn = res);
  }

  private initReviewForm() {
    this.reviewForm = this.fb.group({
      rating: [null, Validators.required],
      description: [null]
    });
  }

  private loadReviews() {
    this.reviewsSubject.pipe(
      switchMap(params =>
        this.reviewsService.retrieveReviews(params)
          .pipe(catchError(err => EMPTY))),
      takeUntil(this.destroy)
    ).subscribe(({reviewResults, averageReviews}) => {
      this.reviews = reviewResults;
      this.averageReviews = averageReviews;
    });
    this.reviewsSubject.next({
      page: this.page, pageSize: this.pageSize, rentalSpaceReference: this.rentalSpaceDetails.rentalSpaceReference
    });
  }

  private initLocationMap() {
    this.locationMapOptions = {
      layers: this.getLocationLayers(),
      zoom: 15,
      center: new Leaflet.LatLng(this.rentalSpaceDetails.location.latitude, this.rentalSpaceDetails.location.longitude)
    };
  }

  private initTransportationMap() {
    this.transportationMapOptions = {
      layers: this.getTransportationLayers(),
      zoom: 12,
      center: new Leaflet.LatLng(this.rentalSpaceDetails.location.latitude, this.rentalSpaceDetails.location.longitude)
    };
  }

  private getLocationLayers(): Leaflet.Layer[] {
    return [
      new Leaflet.TileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        attribution: '&copy; OpenStreetMap contributors'
      } as Leaflet.TileLayerOptions),
      this.createMarker(this.rentalSpaceDetails.location.latitude, this.rentalSpaceDetails.location.longitude)
    ] as Leaflet.Layer[];
  };

  private getTransportationLayers(): Leaflet.Layer[] {
    return [
      new Leaflet.TileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        attribution: '&copy; OpenStreetMap contributors'
      } as Leaflet.TileLayerOptions),
      this.createMarker(this.rentalSpaceDetails.location.latitude, this.rentalSpaceDetails.location.longitude),
      ...this.getTransportationMarkers()
    ] as Leaflet.Layer[];
  }

  private getTransportationMarkers(): Leaflet.Marker[] {
    return this.rentalSpaceDetails.transportationAccess.map(transportation =>
      this.createMarker(transportation.latitude, transportation.longitude)
    );
  }

  private createMarker(latitude: number, longitude: number): Leaflet.Marker {
    return new Leaflet.Marker(new Leaflet.LatLng(latitude, longitude), {
      icon: new Leaflet.Icon({
        iconSize: [50, 41],
        iconAnchor: [13, 41],
        iconUrl: 'assets/blue-marker.svg',
      }),
      title: 'Location'
    } as Leaflet.MarkerOptions);
  };

  private loadImages() {
    this.rentalSpaceService.retrieveRentalImagesFromRentalSpaceDetails(this.rentalSpaceDetails)
      .pipe(takeUntil(this.destroy)).subscribe(images => this.images = images);
  }
}
