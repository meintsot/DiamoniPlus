interface FaultType {
  chars: string;
  "string": string;
  valueType: string;
}

export interface ValidationFault {
  fault: FaultType
}

export interface LoginReqMsgType {
  username: string;
  password: string;
}

export interface LoginRespMsgType {
  jwt: string;
  userId: string;
}

export interface RegisterReqMsgType {
  username: string;
  password: string;
  passwordConfirmation: string;
  firstName: string;
  lastName: string;
  email: string;
  phone: string;
  desiredRole: string;
  avatar?: ImageFileType;
}

export interface RegisterRespMsgType {
  jwt: string;
}

export interface RetrieveUserInfoRespMsgType {
  username: string;
  role: string;
  avatar?: ImageFileType;
  firstName: string;
  lastName: string;
}

export interface ImageFileType {
  binaryIdentification?: string;
  data: string;
  filename: string;
  mime: string;
}

export interface MyRentalSpacesReqMsgType {
  page: number;
  pageSize: number;
}

export interface MyRentalSpacesRespMsgType {
  rentalSpaceResults: RentalSpaceResultType[];
  totalResults: number;
}

export enum RoomContentType {
  Apartment = 'Apartment',
  House = 'House',
  Villa = 'Villa',
  Cottage = 'Cottage',
  Bungalow = 'Bungalow',
  Chalet = 'Chalet',
  Cabin = 'Cabin',
  OfficeSpace = 'Office Space',
  RetailSpace = 'Retail Space',
  Warehouse = 'Warehouse',
  CommercialBuilding = 'Commercial Building',
  Castle = 'Castle',
  Farmhouse = 'Farmhouse',
  Yacht = 'Yacht',
  Treehouse = 'Treehouse',
  Campsite = 'Campsite',
  NewlyBuilt = 'Newly Built',
  StudentApartment = 'Student Apartment',
  DormitoryRoom = 'Dormitory Room',
}

export interface RentalSpaceResultType {
  rentalImageIdentification: string;
  rent: number;
  roomType: RoomContentType;
  noOfBeds: number;
  totalReviews: number;
  averageReviews: number;
  rentalSpaceReference: string;
}

export interface RetrieveRentalImageRespMsgType {
  rentalImage: ImageFileType;
}

export interface CreateRentalSpaceReqMsgType {
  title: string;
  description: string;
  roomType: RoomContentType;
  noOfBedrooms: number;
  maxNumOfPeople: number;
  noOfBeds: number;
  noOfBathrooms: number;
  hasLivingRoom: boolean;
  area: number;
  smokingAllowed: boolean;
  petsAllowed: boolean;
  eventsAllowed: boolean;
  minDuration: number;
  rent: number;
  additionalRentPerPerson: number;
  amenities: AmenitiesType;
  availableRentPeriods: RentalSpaceDateRangeType[];
  location: LocationType;
  transportationAccess: TransportationAccessType[];
  rentalImageIdentifications: string[];
}

export interface CreateRentalSpaceRespMsgType {
  rentalSpaceReference: string;
}

export interface AmenitiesType {
  wirelessInternet: boolean;
  cooling: boolean;
  heating: boolean;
  kitchen: boolean;
  television: boolean;
  parkingSpace: boolean;
  elevator: boolean;
}

export interface RentalSpaceDateRangeType {
  startDate: string;
  endDate: string;
}

export interface LocationType {
  country: string;
  city: string;
  address: string;
  suburb: string;
  postcode: string;
  latitude: number;
  longitude: number;
}

export interface TransportationAccessType {
  transportationName: string;
  transportationType: TransportationContentType;
}

export enum TransportationContentType {
  BUS = 'Bus',
  TRAIN = 'Train',
  SUBWAY = 'Subway',
  AIRPORT = 'Airport',
  OTHER = 'Other'
}

export interface CreateRentalSpaceRespMsgType {
  rentalSpaceReference: string;
}

export interface UploadRentalImageReqMsgType {
  rentalImage: ImageFileType;
}

export interface UploadRentalImageRespMsgType {
  binaryIdentification: string;
}
