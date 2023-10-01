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
  latitude: number;
  longitude: number;
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

export interface SearchRentalSpacesReqMsgType {
  location: LocationType;
  startDate: string;
  endDate: string;
  maxNumOfPeople: number;
  page: number;
  pageSize: number;
  roomType: RoomContentType;
  maximumCost: number;
  amenities: AmenitiesType;
}

export interface SearchRentalSpacesRespMsgType {
  rentalSpaceResults: RentalSpaceResultType[];
  totalResults: number;
}

export interface RetrieveRentalSpaceDetailsRespMsgType {
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
  averageReviews: number;
  totalReviews: number;
  location: LocationType;
  transportationAccess: TransportationAccessType[];
  rentalSpaceReference: string;
  host: string;
  rentalImageIdentifications: string[];
  bookingReference: string;
  bookedDateRange: RentalSpaceDateRangeType;
}

export interface RetrieveReviewsReqMsgType {
  page: number;
  pageSize: number;
  username?: string;
  rentalSpaceReference?: string;
}

export interface RetrieveReviewsRespMsgType {
  reviewResults: ReviewResultType[];
  averageReviews: number;
}

export interface ReviewResultType {
  rating: number;
  description: string;
  author: string;
  authorImageIdentification?: string;
}

export interface SubmitReviewReqMsgType {
  rating: number,
  description: string,
  hostUsername?: string,
  bookingReference?: string,
}

export interface ConfirmBookingReqMsgType {
  rentalSpaceReference: string;
  bookingRange: RentalSpaceDateRangeType;
}

export interface MyBookingsReqMsgType {
  page: number;
  pageSize: number;
}

export interface MyBookingsRespMsgType {
  rentalSpaceResults: RentalSpaceResultType[];
  totalResults: number;
}

export interface GetUserProfileRespMsgType {
  username: string;
  firstName: string;
  lastName: string;
  email: string;
  phone: string;
  desiredRole: string;
  avatar?: ImageFileType;
}

export interface UpdateUserProfileReqMsgType {
  firstName: string;
  lastName: string;
  phone: string;
  avatar: ImageFileType;
}

export interface RetrieveUserProfileImageRespMsgType {
  avatar: ImageFileType;
}

export interface RetrieveHostApprovalRespMsgType {
  approved: boolean;
}

export interface SearchUserProfilesReqMsgType {
  page: number;
  pageSize: number;
  username: string;
  email: string;
  firstName: string;
  lastName: string;
  phone: string;
  roleType: string;
  isHostApproved: boolean;
  averageReviews: number;
}

export interface SearchUserProfilesRespMsgType {
  userProfileResults: UserProfileResult[];
  totalResults: number;
}

export interface UserProfileResult {
  username: string;
  email: string;
  firstName: string;
  lastName: string;
  phone: string;
  roleType: string;
  isHostApproved: boolean;
  averageReviews: number;
  avatarIdentification: string;
}

export interface CreateAndRetrieveDiscussionReqMsgType {
  hostUsername: string;
}

export interface CreateAndRetrieveDiscussionRespMsgType {
  discussionReference: string;
}

export interface RetrieveDiscussionsRespMsgType {
  discussions: DiscussionType[];
}

export interface DiscussionType {
  discussionReference: string;
  username: string;
}

export interface RetrieveMessagesReqMsgType {
  discussionReference: string;
  page: number;
  pageSize: number;
}

export interface RetrieveMessagesRespMsgType {
  messages: MessageType[];
}

export interface MessageType {
  sender: string;
  receiver: string;
  messageText: string;
  messageId?: string;
  deleted: boolean;
  createdAt: string;
}

export interface SaveMessageReqMsgType {
  discussionReference: string;
  message: MessageType;
}

export interface UpdateRentalSpaceDetailsReqMsgType {
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
  rentalSpaceReference: string;
}
