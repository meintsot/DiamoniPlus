import {Component, OnInit} from '@angular/core';
import {RentalSpaceResultType} from "../model";
import {RentalSpaceService} from "../services/rental-space.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-my-rental-spaces',
  templateUrl: './my-rental-spaces.component.html',
  styleUrls: ['./my-rental-spaces.component.css']
})
export class MyRentalSpacesComponent implements OnInit {

  rentalSpaces: RentalSpaceResultType[] = [];

  imageUrls: string[] = [];

  totalResults = 0;

  page = 1;
  pageSize = 10;

  constructor(
    private rentalSpaceService: RentalSpaceService,
    private router: Router
  ) {}

  ngOnInit(): void {
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

  onPageChange($event: { page: number; pageSize: number }) {
    this.page = $event.page;
    this.pageSize = $event.pageSize;
    this.loadMyRentalSpaces();
  }
}
