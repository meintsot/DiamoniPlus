import {Component, OnInit} from '@angular/core';
import {FormArray, FormBuilder, FormGroup, Validators} from "@angular/forms";
import {RoomContentType, TransportationContentType} from "../model";
import {MenuItem} from "primeng/api";
import * as Leaflet from 'leaflet';
import {DatePipe} from "@angular/common";
import {RentalSpaceService} from "../services/rental-space.service";
import {Utils} from "../Utils";
import {InfoService} from "../services/info.service";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-submit-rental-space',
  templateUrl: './submit-rental-space.component.html',
  styleUrls: ['./submit-rental-space.component.css']
})
export class SubmitRentalSpaceComponent implements OnInit {
  roomTypeOptions = Object.values(RoomContentType);
  transportationTypeOptions = Object.values(TransportationContentType);
  submitRentalSpaceForm!: any;
  activeIndex = 1;
  items!: MenuItem[];
  titles!: string[];
  options!: Leaflet.MapOptions;
  locationMarker!: Leaflet.Marker;
  transportationAccessMarkers: Leaflet.Marker[] = [];
  uploadedImages: string[] = [];
  isEdit = false;

  constructor(
    private fb: FormBuilder,
    private datePipe: DatePipe,
    private rentalSpaceService: RentalSpaceService,
    private infoService: InfoService,
    private router: Router,
    private route: ActivatedRoute
  ) {
  }

  ngOnInit() {
    this.initTitles();
    this.initItems();
    this.initForm();
    this.initMap();
    this.checkIfEdit();
  }

  private checkIfEdit() {
    const rentalSpaceReference = this.route.snapshot.queryParams['rentalSpaceReference'];
    if (!Utils.isStringBlank(rentalSpaceReference)) {
      this.rentalSpaceService.retrieveRentalSpaceDetails(rentalSpaceReference).subscribe(res => {
        this.submitRentalSpaceForm.patchValue(res);
        this.isEdit = true;
      })
    }
  }

  private initMap() {
    this.options = {
      layers: this.getLayers(),
      zoom: 12,
      center: new Leaflet.LatLng(43.530147, 16.488932)
    };
  }

  private getLayers(): Leaflet.Layer[] {
    return [
      new Leaflet.TileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        attribution: '&copy; OpenStreetMap contributors'
      } as Leaflet.TileLayerOptions),
    ] as Leaflet.Layer[];
  };

  private createMarker(latitude: number, longitude: number): Leaflet.Marker {
    return new Leaflet.Marker(new Leaflet.LatLng(latitude, longitude), {
      icon: new Leaflet.Icon({
        iconSize: [50, 41],
        iconAnchor: [13, 41],
        iconUrl: 'assets/blue-marker.svg',
      }),
      title: 'Workspace'
    } as Leaflet.MarkerOptions);
  };

  onLocationSet($event: Leaflet.LeafletMouseEvent): void {
    const locationForm = (this.submitRentalSpaceForm.get('location') as FormGroup);
    const lat = $event.latlng.lat;
    const lng = $event.latlng.lng;

    // Create a marker at the clicked location
    this.locationMarker?.remove();
    this.locationMarker = this.createMarker(lat, lng).addTo($event.target);

    // Update the form controls with the clicked coordinates
    locationForm.get('latitude')?.patchValue(lat);
    locationForm.get('longitude')?.patchValue(lng);
  }

  addTransportationAccess($event: Leaflet.LeafletMouseEvent) {
    const transportationAccess = (this.submitRentalSpaceForm.get('transportationAccess') as FormArray);
    const lat = $event.latlng.lat;
    const lng = $event.latlng.lng;

    // Create a marker at the clicked location
    this.transportationAccessMarkers.push(this.createMarker(lat, lng).addTo($event.target));

    // Update the form controls with the clicked coordinates
    transportationAccess.push(this.fb.group({
      transportationName: '',
      transportationType: TransportationContentType.BUS,
      latitude: lat,
      longitude: lng
    }));
  }

  private initTitles() {
    this.titles = [
      'What property do you want to create?', 'Let\'s provide some extra details', 'Any additional things to offer?',
      'For which date periods?', 'Where is it located?', 'How to access it?', 'Let\'s provide some images'
    ];
  }

  private initItems() {
    this.items = [
      {label: 'Property'}, {label: 'Specifications'}, {label: 'Amenities'},
      {label: 'Rent Periods'}, {label: 'Location'}, {label: 'Transportation Access'}, {label: 'Images'}
    ]
    this.activeIndex = 0;
  }

  private initForm() {
    this.submitRentalSpaceForm = this.fb.group({
      title: ['', Validators.required],
      description: ['', Validators.required],
      roomType: [null, Validators.required],
      noOfBedrooms: [null, [Validators.required, Validators.email]],
      maxNumOfPeople: [null, Validators.required],
      noOfBeds: [null, Validators.required],
      noOfBathrooms: [null, Validators.required],
      hasLivingRoom: [false, Validators.required],
      area: [null, Validators.required],
      smokingAllowed: [false, Validators.required],
      petsAllowed: [false, Validators.required],
      eventsAllowed: [false, Validators.required],
      minDuration: [null, Validators.required],
      rent: [null, Validators.required],
      additionalRentPerPerson: [null, Validators.required],
      amenities: this.fb.group({
        wirelessInternet: [false, Validators.required],
        cooling: [false, Validators.required],
        heating: [false, Validators.required],
        kitchen: [false, Validators.required],
        television: [false, Validators.required],
        parkingSpace: [false, Validators.required],
        elevator: [false, Validators.required],
      }),
      availableRentPeriods: this.fb.array([], [Validators.required]),
      location: this.fb.group({
        country: ['', Validators.required],
        city: ['', Validators.required],
        address: ['', Validators.required],
        suburb: ['', Validators.required],
        postcode: ['', Validators.required],
        latitude: [null, Validators.required],
        longitude: [null, Validators.required],
      }),
      transportationAccess: this.fb.array([], [Validators.required]),
      rentalImageIdentifications: this.fb.array([], [Validators.required])
    });
  }

  submitForm() {
    this.rentalSpaceService.createRentalSpace(this.submitRentalSpaceForm.value).subscribe((res) => {
      this.infoService.sendSuccess('Successfully created rental space!');
      this.router.navigate(['my-rental-spaces']);
    });
  }

  activeIndexChange(index: number) {
    this.activeIndex = index;
  }

  addRentPeriod() {
    const availableRentPeriods = (this.submitRentalSpaceForm.get('availableRentPeriods') as FormArray);
    availableRentPeriods.push(this.fb.group({
      startDate: [null, Validators.required],
      endDate: [null, Validators.required]
    }));
  }

  removeTransportationAccess(index: number) {
    const transportationAccess = (this.submitRentalSpaceForm.get('transportationAccess') as FormArray);
    transportationAccess.removeAt(index);
    this.transportationAccessMarkers[index].remove();
    this.transportationAccessMarkers.splice(index, 1)
  }

  removeAvailableRentPeriod(index: number) {
    const availableRentPeriods = (this.submitRentalSpaceForm.get('availableRentPeriods') as FormArray);
    availableRentPeriods.removeAt(index);
  }

  onUpload(event: Event): void {
    const reader = new FileReader();
    if (event.target && (event.target as HTMLInputElement).files && (event.target as HTMLInputElement).files!.length > 0) {
      const file = (event.target as HTMLInputElement).files![0];
      const mime = file.type; // Get the MIME type
      const filename = file.name; // Get the filename
      reader.readAsDataURL(file);

      reader.onload = () => {
        const dataURL = reader.result as string;
        this.rentalSpaceService.uploadRentalImage({
          rentalImage: {
            data: Utils.removeDataURL(dataURL.slice()),
            filename,
            mime
          }
        }).subscribe(binaryIdentification => {
          const rentalImageIdentifications= (this.submitRentalSpaceForm.get('rentalImageIdentifications') as FormArray);
          rentalImageIdentifications.push(this.fb.control(binaryIdentification));
          this.uploadedImages.push(dataURL);
        });
      };
    }
  }

  deleteImage(i: number) {
    const rentalImageIdentifications= (this.submitRentalSpaceForm.get('rentalImageIdentifications') as FormArray);
    this.rentalSpaceService.deleteRentalImage(rentalImageIdentifications.at(i).value).subscribe(() => {
      rentalImageIdentifications.removeAt(i);
      this.uploadedImages.splice(i, 1);
    })
  }

  assignStartDate(rentPeriod: FormGroup, $event: Date) {
    const date = this.datePipe.transform($event, 'yyyy-MM-dd HH:mm:ss');
    rentPeriod.get('startDate')?.patchValue(date);
  }

  assignEndDate(rentPeriod: FormGroup, $event: Date) {
    const date = this.datePipe.transform($event, 'yyyy-MM-dd HH:mm:ss');
    rentPeriod.get('endDate')?.patchValue(date);
  }
}
