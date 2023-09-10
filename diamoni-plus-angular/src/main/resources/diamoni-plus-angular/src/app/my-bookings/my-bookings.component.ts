import {Component, OnInit} from '@angular/core';
import {RentalSpaceResultType} from "../model";
import {BookingService} from "../services/booking.service";
import {RentalSpaceService} from "../services/rental-space.service";

@Component({
  selector: 'app-my-bookings',
  templateUrl: './my-bookings.component.html',
  styleUrls: ['./my-bookings.component.css']
})
export class MyBookingsComponent implements OnInit {
  rentalSpaces: RentalSpaceResultType[] = [];

  imageUrls: string[] = [];

  totalResults = 0;

  page = 1;
  pageSize = 10;

  constructor(
    private rentalSpaceService: RentalSpaceService,
    private bookingService: BookingService
  ) {}

  ngOnInit(): void {
    this.loadMyRentalSpaces();
  }

  loadMyRentalSpaces() {
    this.bookingService.myBookings({page: this.page, pageSize: this.pageSize})
      .subscribe(res => {
        this.handleRentalImages(res.rentalSpaceResults);
        this.rentalSpaces = res.rentalSpaceResults;
        this.totalResults = res.totalResults;
      });
  }

  private handleRentalImages(rentalSpaceResults: RentalSpaceResultType[]) {
    this.rentalSpaceService.retrieveRentalImages(rentalSpaceResults)
      .subscribe(res => this.imageUrls = res);
  }

  onPageChange($event: { page: number; pageSize: number }) {
    this.page = $event.page;
    this.pageSize = $event.pageSize;
    this.loadMyRentalSpaces();
  }
}
