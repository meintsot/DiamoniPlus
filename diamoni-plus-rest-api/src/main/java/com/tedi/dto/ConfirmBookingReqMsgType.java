package com.tedi.dto;

public class ConfirmBookingReqMsgType {

    private String rentalSpaceReference;

    private RentalSpaceDateRangeType bookingRange;

    public String getRentalSpaceReference() {
        return rentalSpaceReference;
    }

    public void setRentalSpaceReference(String rentalSpaceReference) {
        this.rentalSpaceReference = rentalSpaceReference;
    }

    public RentalSpaceDateRangeType getBookingRange() {
        return bookingRange;
    }

    public void setBookingRange(RentalSpaceDateRangeType bookingRange) {
        this.bookingRange = bookingRange;
    }
}
