package com.tedi.dto;

public class RentalSpaceResultType {

    private String rentalImageIdentification;
    private Double rent;
    private RoomContentType roomType;
    private Integer noOfBeds;
    private Integer totalReviews;
    private Double averageReviews;
    private String rentalSpaceReference;

    public String getRentalImageIdentification() {
        return rentalImageIdentification;
    }

    public void setRentalImageIdentification(String rentalImageIdentification) {
        this.rentalImageIdentification = rentalImageIdentification;
    }

    public Double getRent() {
        return rent;
    }

    public void setRent(Double rent) {
        this.rent = rent;
    }

    public RoomContentType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomContentType roomType) {
        this.roomType = roomType;
    }

    public Integer getNoOfBeds() {
        return noOfBeds;
    }

    public void setNoOfBeds(Integer noOfBeds) {
        this.noOfBeds = noOfBeds;
    }

    public Integer getTotalReviews() {
        return totalReviews;
    }

    public void setTotalReviews(Integer totalReviews) {
        this.totalReviews = totalReviews;
    }

    public Double getAverageReviews() {
        return averageReviews;
    }

    public void setAverageReviews(Double averageReviews) {
        this.averageReviews = averageReviews;
    }

    public String getRentalSpaceReference() {
        return rentalSpaceReference;
    }

    public void setRentalSpaceReference(String rentalSpaceReference) {
        this.rentalSpaceReference = rentalSpaceReference;
    }
}
