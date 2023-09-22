package com.tedi.dto;

import com.tedi.model.RoleType;

public class UserProfileResult {

    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private String roleType;
    private Boolean isHostApproved;
    private Double averageReviews;
    private String avatarIdentification;

    public UserProfileResult(
            String username, String email, String firstName,
            String lastName, String phone, RoleType roleType,
            Boolean isHostApproved, Double averageReviews,
            String avatarIdentification
    ) {
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.roleType = roleType.getValue();
        this.isHostApproved = isHostApproved;
        this.averageReviews = averageReviews;
        this.avatarIdentification = avatarIdentification;
    }

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRoleType() {
        return roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }

    public Boolean getIsHostApproved() {
        return isHostApproved;
    }

    public void setIsHostApproved(Boolean isHostApproved) {
        this.isHostApproved = isHostApproved;
    }

    public Double getAverageReviews() {
        return averageReviews;
    }

    public void setAverageReviews(Double averageReviews) {
        this.averageReviews = averageReviews;
    }

    public String getAvatarIdentification() {
        return avatarIdentification;
    }

    public void setAvatarIdentification(String avatarIdentification) {
        this.avatarIdentification = avatarIdentification;
    }
}
