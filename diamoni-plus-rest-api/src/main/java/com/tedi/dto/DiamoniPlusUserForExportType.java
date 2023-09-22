package com.tedi.dto;

import java.util.ArrayList;
import java.util.List;

public class DiamoniPlusUserForExportType {

    private String username;

    private String email;

    private String firstName;

    private String lastName;

    private String roleType;

    private String phone;

    private Boolean isHostApproved;

    private Double averageReviews;

    private List<RentalSpacesForExportType> myRentalSpaces = new ArrayList<>();

    private List<BookingsForExportType> myBookings = new ArrayList<>();

    private List<ReviewsForExportType> reviews = new ArrayList<>();

    private ImageFileType avatar;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getRoleType() {
        return roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Boolean getHostApproved() {
        return isHostApproved;
    }

    public void setHostApproved(Boolean hostApproved) {
        isHostApproved = hostApproved;
    }

    public Double getAverageReviews() {
        return averageReviews;
    }

    public void setAverageReviews(Double averageReviews) {
        this.averageReviews = averageReviews;
    }

    public List<RentalSpacesForExportType> getMyRentalSpaces() {
        return myRentalSpaces;
    }

    public void setMyRentalSpaces(List<RentalSpacesForExportType> myRentalSpaces) {
        this.myRentalSpaces = myRentalSpaces;
    }

    public List<BookingsForExportType> getMyBookings() {
        return myBookings;
    }

    public void setMyBookings(List<BookingsForExportType> myBookings) {
        this.myBookings = myBookings;
    }

    public List<ReviewsForExportType> getReviews() {
        return reviews;
    }

    public void setReviews(List<ReviewsForExportType> reviews) {
        this.reviews = reviews;
    }

    public ImageFileType getAvatar() {
        return avatar;
    }

    public void setAvatar(ImageFileType avatar) {
        this.avatar = avatar;
    }
}
