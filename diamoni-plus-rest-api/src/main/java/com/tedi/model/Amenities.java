package com.tedi.model;

import jakarta.persistence.Embeddable;

@Embeddable
public class Amenities {
    private Boolean wirelessInternet;
    private Boolean cooling;
    private Boolean heating;
    private Boolean kitchen;
    private Boolean television;
    private Boolean parkingSpace;
    private Boolean elevator;

    public Boolean getWirelessInternet() {
        return wirelessInternet;
    }

    public void setWirelessInternet(Boolean wirelessInternet) {
        this.wirelessInternet = wirelessInternet;
    }

    public Boolean getCooling() {
        return cooling;
    }

    public void setCooling(Boolean cooling) {
        this.cooling = cooling;
    }

    public Boolean getHeating() {
        return heating;
    }

    public void setHeating(Boolean heating) {
        this.heating = heating;
    }

    public Boolean getKitchen() {
        return kitchen;
    }

    public void setKitchen(Boolean kitchen) {
        this.kitchen = kitchen;
    }

    public Boolean getTelevision() {
        return television;
    }

    public void setTelevision(Boolean television) {
        this.television = television;
    }

    public Boolean getParkingSpace() {
        return parkingSpace;
    }

    public void setParkingSpace(Boolean parkingSpace) {
        this.parkingSpace = parkingSpace;
    }

    public Boolean getElevator() {
        return elevator;
    }

    public void setElevator(Boolean elevator) {
        this.elevator = elevator;
    }
}
