package com.tedi.dto;

import java.util.ArrayList;
import java.util.List;

public class RetrieveRentalSpaceDetailsRespMsgType {

    private String title;
    private String description;
    private RoomContentType roomType;
    private Integer noOfBedrooms;
    private Integer maxNumOfPeople;
    private Integer noOfBeds;
    private Integer noOfBathrooms;
    private Boolean hasLivingRoom;
    private Integer area;
    private Boolean smokingAllowed;
    private Boolean petsAllowed;
    private Boolean eventsAllowed;
    private Integer minDuration;
    private Double rent;
    private Double additionalRentPerPerson;
    private AmenitiesType amenities;
    private List<RentalSpaceDateRangeType> availableRentPeriods = new ArrayList<>();
    private Double averageReviews;
    private Integer totalReviews;
    private LocationType location;
    private List<TransportationAccessType> transportationAccess = new ArrayList<>();
    private String rentalSpaceReference;
    private String host;
    private List<String> rentalImageIdentifications = new ArrayList<>();
    private String bookingReference;
    private RentalSpaceDateRangeType bookedDateRange;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public RoomContentType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomContentType roomType) {
        this.roomType = roomType;
    }

    public Integer getNoOfBedrooms() {
        return noOfBedrooms;
    }

    public void setNoOfBedrooms(Integer noOfBedrooms) {
        this.noOfBedrooms = noOfBedrooms;
    }

    public Integer getMaxNumOfPeople() {
        return maxNumOfPeople;
    }

    public void setMaxNumOfPeople(Integer maxNumOfPeople) {
        this.maxNumOfPeople = maxNumOfPeople;
    }

    public Integer getNoOfBeds() {
        return noOfBeds;
    }

    public void setNoOfBeds(Integer noOfBeds) {
        this.noOfBeds = noOfBeds;
    }

    public Integer getNoOfBathrooms() {
        return noOfBathrooms;
    }

    public void setNoOfBathrooms(Integer noOfBathrooms) {
        this.noOfBathrooms = noOfBathrooms;
    }

    public Boolean getHasLivingRoom() {
        return hasLivingRoom;
    }

    public void setHasLivingRoom(Boolean hasLivingRoom) {
        this.hasLivingRoom = hasLivingRoom;
    }

    public Integer getArea() {
        return area;
    }

    public void setArea(Integer area) {
        this.area = area;
    }

    public Boolean getSmokingAllowed() {
        return smokingAllowed;
    }

    public void setSmokingAllowed(Boolean smokingAllowed) {
        this.smokingAllowed = smokingAllowed;
    }

    public Boolean getPetsAllowed() {
        return petsAllowed;
    }

    public void setPetsAllowed(Boolean petsAllowed) {
        this.petsAllowed = petsAllowed;
    }

    public Boolean getEventsAllowed() {
        return eventsAllowed;
    }

    public void setEventsAllowed(Boolean eventsAllowed) {
        this.eventsAllowed = eventsAllowed;
    }

    public Integer getMinDuration() {
        return minDuration;
    }

    public void setMinDuration(Integer minDuration) {
        this.minDuration = minDuration;
    }

    public Double getRent() {
        return rent;
    }

    public void setRent(Double rent) {
        this.rent = rent;
    }

    public Double getAdditionalRentPerPerson() {
        return additionalRentPerPerson;
    }

    public void setAdditionalRentPerPerson(Double additionalRentPerPerson) {
        this.additionalRentPerPerson = additionalRentPerPerson;
    }

    public AmenitiesType getAmenities() {
        return amenities;
    }

    public void setAmenities(AmenitiesType amenities) {
        this.amenities = amenities;
    }

    public List<RentalSpaceDateRangeType> getAvailableRentPeriods() {
        return availableRentPeriods;
    }

    public void setAvailableRentPeriods(List<RentalSpaceDateRangeType> availableRentPeriods) {
        this.availableRentPeriods = availableRentPeriods;
    }

    public Double getAverageReviews() {
        return averageReviews;
    }

    public void setAverageReviews(Double averageReviews) {
        this.averageReviews = averageReviews;
    }

    public Integer getTotalReviews() {
        return totalReviews;
    }

    public void setTotalReviews(Integer totalReviews) {
        this.totalReviews = totalReviews;
    }

    public LocationType getLocation() {
        return location;
    }

    public void setLocation(LocationType location) {
        this.location = location;
    }

    public List<TransportationAccessType> getTransportationAccess() {
        return transportationAccess;
    }

    public void setTransportationAccess(List<TransportationAccessType> transportationAccess) {
        this.transportationAccess = transportationAccess;
    }

    public String getRentalSpaceReference() {
        return rentalSpaceReference;
    }

    public void setRentalSpaceReference(String rentalSpaceReference) {
        this.rentalSpaceReference = rentalSpaceReference;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public List<String> getRentalImageIdentifications() {
        return rentalImageIdentifications;
    }

    public void setRentalImageIdentifications(List<String> rentalImageIdentifications) {
        this.rentalImageIdentifications = rentalImageIdentifications;
    }

    public String getBookingReference() {
        return bookingReference;
    }

    public void setBookingReference(String bookingReference) {
        this.bookingReference = bookingReference;
    }

    public RentalSpaceDateRangeType getBookedDateRange() {
        return bookedDateRange;
    }

    public void setBookedDateRange(RentalSpaceDateRangeType bookedDateRange) {
        this.bookedDateRange = bookedDateRange;
    }
}
