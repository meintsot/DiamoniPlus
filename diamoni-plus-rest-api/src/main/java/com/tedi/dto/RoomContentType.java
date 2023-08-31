package com.tedi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum RoomContentType {
    @JsonProperty("Apartment")
    APARTMENT("Apartment"),
    @JsonProperty("House")
    HOUSE("House"),
    @JsonProperty("Villa")

    VILLA("Villa"),
    @JsonProperty("Cottage")
    COTTAGE("Cottage"),
    @JsonProperty("Bungalow")
    BUNGALOW("Bungalow"),
    @JsonProperty("Chalet")
    CHALET("Chalet"),
    @JsonProperty("Cabin")
    CABIN("Cabin"),
    @JsonProperty("Office Space")
    OFFICE_SPACE("Office Space"),
    @JsonProperty("Retail Space")
    RETAIL_SPACE("Retail Space"),
    @JsonProperty("Warehouse")
    WAREHOUSE("Warehouse"),
    @JsonProperty("Commercial Building")
    COMMERCIAL_BUILDING("Commercial Building"),
    @JsonProperty("Castle")
    CASTLE("Castle"),
    @JsonProperty("Farmhouse")
    FARMHOUSE("Farmhouse"),
    @JsonProperty("Yacht")
    YACHT("Yacht"),
    @JsonProperty("Treehouse")
    TREEHOUSE("Treehouse"),
    @JsonProperty("Campsite")
    CAMPSITE("Campsite"),
    @JsonProperty("Newly Built")
    NEWLY_BUILT("Newly Built"),
    @JsonProperty("Student Apartment")
    STUDENT_APARTMENT("Student Apartment"),
    @JsonProperty("Dormitory Room")
    DORMITORY_ROOM("Dormitory Room");

    private final String value;

    RoomContentType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static RoomContentType fromValue(String value) {
        for (RoomContentType roleType : RoomContentType.values()) {
            if (roleType.value.equals(value)) {
                return roleType;
            }
        }
        throw new IllegalArgumentException(value);
    }

    @Override
    public String toString() {
        return value; // Return the enum constant's name instead of its value
    }
}
