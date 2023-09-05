import {Component, OnInit} from '@angular/core';
import {RentalSpaceResultType} from "../../model";
import {RentalSpaceService} from "../../services/rental-space.service";
import {tap} from "rxjs";
import {Router} from "@angular/router";

@Component({
  selector: 'app-rental-space-results',
  templateUrl: './rental-space-results.component.html',
  styleUrls: ['./rental-space-results.component.css']
})
export class RentalSpaceResultsComponent implements OnInit {

  cols: any[] = [];

  rentalSpaces: RentalSpaceResultType[] = [];

  imageUrls: string[] = [];

  totalResults = 0;

  page = 1;
  pageSize = 10;

  constructor(
    private rentalSpaceService: RentalSpaceService,
    private router: Router
  ) {}

  ngOnInit() {
    this.cols = [
      {field: 'image', header: 'Image'},
      {field: 'rent', header: 'Rent'},
      {field: 'roomType', header: 'Room Type'},
      {field: 'numberOfBeds', header: 'Number of Beds'},
      {field: 'totalReviews', header: 'Total Reviews'},
      {field: 'averageReviews', header: 'Average Reviews'}
    ];
    this.loadMyRentalSpaces();
  }

  loadMyRentalSpaces() {
    this.rentalSpaceService.myRentalSpaces({page: this.page, pageSize: this.pageSize})
      .subscribe(res => {
        this.handleRentalImages(res.rentalSpaceResults);
        this.rentalSpaces = res.rentalSpaceResults;
        this.totalResults = res.totalResults;
      });
  }

  redirectToRentalSpaceFormPage() {
    this.router.navigate(['submit-rental-space']);
  }

  private handleRentalImages(rentalSpaceResults: RentalSpaceResultType[]) {
    this.rentalSpaceService.retrieveRentalImages(rentalSpaceResults)
      .subscribe(res => this.imageUrls = res);
  }
}
