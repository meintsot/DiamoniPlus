package com.tedi.fault;

public enum ErrorMessageType {

    MESS_01_AuthService("Username must be at least 5 characters long."),
    MESS_02_AuthService("Password must be at least 5 characters long."),
    MESS_03_AuthService("Password and password confirmation must match."),
    MESS_04_AuthService("Email must have a specific format (ex: somewhat@gmail.com)."),

    DATA_01_AuthService("Username must be unique."),
    DATA_02_AuthService("Email must be unique."),
    DATA_03_AuthService("Invalid username or password."),
    DATA_04_AuthService("Invalid password for the current user."),
    DATA_05_AuthService("Invalid JWT credentials."),

    MESS_01_RentalSpaceService("Space area must be larger than 20 sq.m."),
    MESS_02_RentalSpaceService("Host is not approved by administration."),
    MESS_03_RentalSpaceService("Rental space does not belong to this user."),
    MESS_04_RentalSpaceService("At least one rental space image is required."),

    DATA_01_RentalSpaceService("Host not found."),
    DATA_02_RentalSpaceService("Rental space not found."),
    DATA_03_RentalSpaceService("Rental space image not found."),

    MESS_01_ReviewsService("Either host username or rental space uuid is required."),
    MESS_02_ReviewsService("Cannot submit a review for a host user and a rental space simultaneously."),
    MESS_03_ReviewsService("Either host username or rental space reference is required."),

    DATA_01_ReviewsService("Tenant not found!"),
    DATA_02_ReviewsService("Host not found!"),
    DATA_03_ReviewsService("Booking not found!"),

    DATA_01_BookingService("Tenant not found."),
    DATA_02_BookingService("Rental space not found."),
    DATA_03_BookingService("Booking should be in a valid date range."),
    DATA_04_BookingService("Booking not found!");

    private final String error;

    ErrorMessageType(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }
}
