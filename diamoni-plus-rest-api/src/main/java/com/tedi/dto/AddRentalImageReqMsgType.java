package com.tedi.dto;

public class AddRentalImageReqMsgType {

    String rentalSpaceReference;

    RentalImageType rentalImage;

    public String getRentalSpaceReference() {
        return rentalSpaceReference;
    }

    public void setRentalSpaceReference(String rentalSpaceReference) {
        this.rentalSpaceReference = rentalSpaceReference;
    }

    public RentalImageType getRentalImage() {
        return rentalImage;
    }

    public void setRentalImage(RentalImageType rentalImage) {
        this.rentalImage = rentalImage;
    }
}
