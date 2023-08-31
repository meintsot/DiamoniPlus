package com.tedi.dto;

import java.time.LocalDateTime;

public class SearchRentalSpacesReqMsgType {

    private LocationType location;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Integer maxNumOfPeople;
    private Integer page;
    private Integer pageSize;
    private RoomContentType roomType;
    private Integer maximumCost;
    private AmenitiesType amenities;

    public LocationType getLocation() {
        return location;
    }

    public void setLocation(LocationType location) {
        this.location = location;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public Integer getMaxNumOfPeople() {
        return maxNumOfPeople;
    }

    public void setMaxNumOfPeople(Integer maxNumOfPeople) {
        this.maxNumOfPeople = maxNumOfPeople;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public RoomContentType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomContentType roomType) {
        this.roomType = roomType;
    }

    public Integer getMaximumCost() {
        return maximumCost;
    }

    public void setMaximumCost(Integer maximumCost) {
        this.maximumCost = maximumCost;
    }

    public AmenitiesType getAmenities() {
        return amenities;
    }

    public void setAmenities(AmenitiesType amenities) {
        this.amenities = amenities;
    }
}
