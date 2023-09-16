import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {RentalSpaceResultType} from "../../model";
import {DataViewPageEvent} from "primeng/dataview";
import {NavigationExtras, Router} from "@angular/router";
@Component({
  selector: 'app-rental-space-results',
  templateUrl: './rental-space-results.component.html',
  styleUrls: ['./rental-space-results.component.css']
})
export class RentalSpaceResultsComponent implements OnInit {

  cols: any[] = [];

  @Input() rentalSpaces: RentalSpaceResultType[] = [];

  @Input() imageUrls: string[] = [];

  @Input() totalResults = 0;

  @Input() isHost = false;

  @Output() pageChange = new EventEmitter<{page: number, pageSize: number}>();

  constructor(private router: Router) {}

  ngOnInit() {
    this.cols = [
      {field: 'image', header: 'Image'},
      {field: 'rent', header: 'Rent'},
      {field: 'roomType', header: 'Room Type'},
      {field: 'numberOfBeds', header: 'Number of Beds'},
      {field: 'totalReviews', header: 'Total Reviews'},
      {field: 'averageReviews', header: 'Average Reviews'}
    ];
  }

  onPageChange($event: DataViewPageEvent) {
    this.pageChange.emit({page: $event.first, pageSize: $event.rows});
  }

  navigateToRentalSpaceDetails(rentalSpace: RentalSpaceResultType) {
    const queryParams: any = {
      rentalSpaceReference: rentalSpace.rentalSpaceReference
    };

    const navigationExtras: NavigationExtras = {
      queryParams,
    };

    this.router.navigate(['/rental-space-details'], navigationExtras);
  }

  navigateToEditRentalSpace(rentalSpace: RentalSpaceResultType) {
    const queryParams: any = {
      rentalSpaceReference: rentalSpace.rentalSpaceReference
    };

    const navigationExtras: NavigationExtras = {
      queryParams,
    };

    this.router.navigate(['/submit-rental-space'], navigationExtras);
  }
}
