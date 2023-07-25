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

    DATA_05_AuthService("Invalid JWT credentials.")

    ;

    private final String error;

    ErrorMessageType(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }
}
