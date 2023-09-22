package com.tedi.dto;

public class BookingsForExportType {

    private String bookingReference;

    private String host;

    private String rentalSpaceReference;

    private RentalSpaceDateRangeType bookingDateRange;

    public String getBookingReference() {
        return bookingReference;
    }

    public void setBookingReference(String bookingReference) {
        this.bookingReference = bookingReference;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getRentalSpaceReference() {
        return rentalSpaceReference;
    }

    public void setRentalSpaceReference(String rentalSpaceReference) {
        this.rentalSpaceReference = rentalSpaceReference;
    }

    public RentalSpaceDateRangeType getBookingDateRange() {
        return bookingDateRange;
    }

    public void setBookingDateRange(RentalSpaceDateRangeType bookingDateRange) {
        this.bookingDateRange = bookingDateRange;
    }
}
