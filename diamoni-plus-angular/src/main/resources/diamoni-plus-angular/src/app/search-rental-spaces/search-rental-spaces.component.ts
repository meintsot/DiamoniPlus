import {Component, OnInit, ViewChild} from '@angular/core';
import {RentalSpaceResultType, RoomContentType} from "../model";
import {RentalSpaceService} from "../services/rental-space.service";
import {FormBuilder, FormGroup} from "@angular/forms";
import {DatePipe} from "@angular/common";
import {debounceTime, distinctUntilChanged} from "rxjs";
import {Calendar} from "primeng/calendar";
import {Utils} from "../Utils";

@Component({
  selector: 'app-search-rental-spaces',
  templateUrl: './search-rental-spaces.component.html',
  styleUrls: ['./search-rental-spaces.component.css']
})
export class SearchRentalSpacesComponent implements OnInit {

  filterForm!: FormGroup;

  rentalSpaces: RentalSpaceResultType[] = [];

  imageUrls: string[] = [];

  totalResults = 0;

  page = 1;
  pageSize = 10;

  roomTypes = Object.values(RoomContentType);

  resetFiltersDisabled = true;

  @ViewChild('startDate') startDate!: Calendar;
  @ViewChild('endDate') endDate!: Calendar;

  constructor(
    private rentalSpaceService: RentalSpaceService,
    private fb: FormBuilder,
    private datePipe: DatePipe
  ) {}

  ngOnInit(): void {
    this.initFilterForm();
  }

  searchRentalSpaces() {
    this.rentalSpaceService.searchRentalSpaces(this.filterForm.value)
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

  private initFilterForm() {
    this.filterForm = this.fb.group({
      location: this.fb.group({
        country: null,
        city: null,
        address: null,
        suburb: null,
        postcode: null,
        latitude: null,
        longitude: null
      }),
      startDate: null,
      endDate: null,
      maxNumOfPeople: null,
      roomType: null,
      maximumCost: null,
      amenities: this.fb.group({
        wirelessInternet: null,
        cooling: null,
        heating: null,
        kitchen: null,
        television: null,
        parkingSpace: null,
        elevator: null,
      })
    });

    // Subscribe to changes in the form controls
    this.filterForm.valueChanges
      .pipe(
        debounceTime(300), // Adjust debounce time as needed
        distinctUntilChanged()
      )
      .subscribe(() => {
        this.searchRentalSpaces();
        if (this.filterForm.dirty) {
          this.resetFiltersDisabled = false;
        }
      });
  }

  assignStartDate($event: Date) {
    const date = this.datePipe.transform($event, 'yyyy-MM-dd HH:mm:ss');
    this.filterForm.get('startDate')?.patchValue(date);
    if (!Utils.isStringBlank(date)) {
      this.resetFiltersDisabled = false;
    }
  }

  assignEndDate($event: Date) {
    const date = this.datePipe.transform($event, 'yyyy-MM-dd HH:mm:ss');
    this.filterForm.get('endDate')?.patchValue(date);
    if (!Utils.isStringBlank(date)) {
      this.resetFiltersDisabled = false;
    }
  }

  clearFilters() {
    this.filterForm.reset();
    this.resetFiltersDisabled = true;
    this.startDate.writeValue(null);
    this.endDate.writeValue(null);
  }

  onPageChange($event: {page: number; pageSize: number}) {
    this.filterForm.get('page')?.patchValue($event.page);
    this.filterForm.get('pageSize')?.patchValue($event.pageSize);
    this.searchRentalSpaces();
  }
}
