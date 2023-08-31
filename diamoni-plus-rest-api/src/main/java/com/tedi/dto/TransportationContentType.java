package com.tedi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum TransportationContentType {

    @JsonProperty("Bus")
    BUS("Bus"),
    @JsonProperty("Train")
    TRAIN("Train"),
    @JsonProperty("Subway")
    SUBWAY("Subway"),
    @JsonProperty("Airport")
    AIRPORT("Airport"),
    @JsonProperty("Other")
    OTHER("Other");

    private final String value;

    TransportationContentType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static TransportationContentType fromValue(String value) {
        for (TransportationContentType TransportationContentType : TransportationContentType.values()) {
            if (TransportationContentType.value.equals(value)) {
                return TransportationContentType;
            }
        }
        throw new IllegalArgumentException(value);
    }
}
