package com.tedi.model;

public enum RoleType {
    HOST("host"),
    TENANT("tenant"),
    GUEST("guest"),
    ADMIN("admin");

    private final String value;

    RoleType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
