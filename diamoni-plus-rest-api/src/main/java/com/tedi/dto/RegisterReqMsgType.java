package com.tedi.dto;

public class RegisterReqMsgType {

    private String username;

    private String password;

    private String passwordConfirmation;

    private String firstName;

    private String lastName;

    private String email;

    private String phone;

    private String desiredRole;

    private ImageFileType avatar;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public void setPasswordConfirmation(String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDesiredRole() {
        return desiredRole;
    }

    public void setDesiredRole(String desiredRole) {
        this.desiredRole = desiredRole;
    }

    public ImageFileType getAvatar() {
        return avatar;
    }

    public void setAvatar(ImageFileType avatar) {
        this.avatar = avatar;
    }
}
