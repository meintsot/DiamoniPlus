package com.tedi.model;

public enum RoleType {
    HOST("host"),
    TENANT("tenant"),
    ADMIN("admin");

    private final String value;

    RoleType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static RoleType fromValue(String value) {
        for (RoleType roleType : RoleType.values()) {
            if (roleType.value.equals(value)) {
                return roleType;
            }
        }
        throw new IllegalArgumentException(value);
    }
}
