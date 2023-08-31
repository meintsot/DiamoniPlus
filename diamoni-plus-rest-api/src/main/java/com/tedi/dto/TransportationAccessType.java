package com.tedi.dto;

public class TransportationAccessType {

    private String transportationName;

    private TransportationContentType transportationType;

    private double latitude;

    private double longitude;

    public String getTransportationName() {
        return transportationName;
    }

    public void setTransportationName(String transportationName) {
        this.transportationName = transportationName;
    }

    public TransportationContentType getTransportationType() {
        return transportationType;
    }

    public void setTransportationType(TransportationContentType transportationType) {
        this.transportationType = transportationType;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
