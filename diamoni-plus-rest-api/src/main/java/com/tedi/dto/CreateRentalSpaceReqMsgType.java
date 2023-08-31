package com.tedi.dto;

import java.util.List;

public class CreateRentalSpaceReqMsgType {

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
    private List<RentalSpaceDateRangeType> availableRentPeriods;
    private LocationType location;
    private List<TransportationAccessType> transportationAccess;
    private List<RentalImageType> rentalImages;

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

    public List<RentalImageType> getRentalImages() {
        return rentalImages;
    }

    public void setRentalImages(List<RentalImageType> rentalImages) {
        this.rentalImages = rentalImages;
    }
}
