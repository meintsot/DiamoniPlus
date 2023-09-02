package com.tedi.dto;

public class UpdateUserProfileReqMsgType {
    private String firstName;
    private String lastName;
    private String phone;
    private ImageFileType avatar;

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

    public ImageFileType getAvatar() {
        return avatar;
    }

    public void setAvatar(ImageFileType avatar) {
        this.avatar = avatar;
    }
}
