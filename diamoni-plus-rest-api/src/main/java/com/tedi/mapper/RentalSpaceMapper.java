package com.tedi.mapper;

import com.tedi.dto.*;
import com.tedi.model.*;
import com.tedi.utils.DataUtils;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class RentalSpaceMapper {

    @Inject
    ImageFileMapper imageFileMapper;

    public RentalSpace toRentalSpace(CreateRentalSpaceReqMsgType param) {
        
        RentalSpace rentalSpace = new RentalSpace();
        rentalSpace.setTitle(param.getTitle());
        rentalSpace.setDescription(param.getDescription());
        rentalSpace.setRoomType(param.getRoomType().getValue());
        rentalSpace.setNoOfBedrooms(param.getNoOfBedrooms());
        rentalSpace.setMaxNumOfPeople(param.getMaxNumOfPeople());
        rentalSpace.setNoOfBeds(param.getNoOfBeds());
        rentalSpace.setNoOfBathrooms(param.getNoOfBathrooms());
        rentalSpace.setHasLivingRoom(param.getHasLivingRoom());
        rentalSpace.setArea(param.getArea());
        rentalSpace.setSmokingAllowed(param.getSmokingAllowed());
        rentalSpace.setPetsAllowed(param.getPetsAllowed());
        rentalSpace.setEventsAllowed(param.getEventsAllowed());
        rentalSpace.setMinDuration(param.getMinDuration());
        rentalSpace.setRent(param.getRent());
        rentalSpace.setAdditionalRentPerPerson(param.getAdditionalRentPerPerson());
        rentalSpace.setAmenities(toAmenities(param.getAmenities()));
        rentalSpace.getAvailableRentPeriods().addAll(toRentalSpaceDateRange(param.getAvailableRentPeriods()));
        Location location = toLocation(param.getLocation());
        location.setRentalSpace(rentalSpace);
        rentalSpace.setLocation(location);
        rentalSpace.setTransportationAccess(toTransportationAccess(param.getTransportationAccess()));
        rentalSpace.setCreatedAt(LocalDateTime.now());
        rentalSpace.setUpdatedAt(LocalDateTime.now());
        rentalSpace.setRentalSpaceReference(UUID.randomUUID().toString());

        return rentalSpace;
    }

    public List<TransportationAccess> toTransportationAccess(List<TransportationAccessType> transportationAccessTypeList) {

        return transportationAccessTypeList.stream().map(this::toTransportationAccess).toList();
    }

    private TransportationAccess toTransportationAccess(TransportationAccessType transportationAccessType) {

        TransportationAccess transportationAccess = new TransportationAccess();
        transportationAccess.setTransportationName(transportationAccessType.getTransportationName());
        transportationAccess.setTransportationType(transportationAccessType.getTransportationType().getValue());
        transportationAccess.setLatitude(transportationAccessType.getLatitude());
        transportationAccess.setLongitude(transportationAccessType.getLongitude());
        transportationAccess.setCreatedAt(LocalDateTime.now());
        transportationAccess.setUpdatedAt(LocalDateTime.now());

        return transportationAccess;
    }

    public List<RentalSpaceDateRange> toRentalSpaceDateRange(List<RentalSpaceDateRangeType> rentalSpaceDateRangeTypeList) {

        return rentalSpaceDateRangeTypeList.stream().map(this::toRentalSpaceDateRange).toList();
    }

    private RentalSpaceDateRange toRentalSpaceDateRange(RentalSpaceDateRangeType rentalSpaceDateRangeType) {

        RentalSpaceDateRange rentalSpaceDateRange = new RentalSpaceDateRange();
        rentalSpaceDateRange.setStartDate(DataUtils.fromStringToLocalDateTime(rentalSpaceDateRangeType.getStartDate()));
        rentalSpaceDateRange.setEndDate(DataUtils.fromStringToLocalDateTime(rentalSpaceDateRangeType.getEndDate()));
        rentalSpaceDateRange.setCreatedAt(LocalDateTime.now());
        rentalSpaceDateRange.setUpdatedAt(LocalDateTime.now());

        return rentalSpaceDateRange;
    }

    private Amenities toAmenities(AmenitiesType amenitiesType) {

        Amenities amenities = new Amenities();
        amenities.setWirelessInternet(amenitiesType.getWirelessInternet());
        amenities.setCooling(amenitiesType.getCooling());
        amenities.setHeating(amenitiesType.getHeating());
        amenities.setKitchen(amenitiesType.getKitchen());
        amenities.setTelevision(amenitiesType.getTelevision());
        amenities.setParkingSpace(amenitiesType.getParkingSpace());
        amenities.setElevator(amenitiesType.getElevator());

        return amenities;
    }

    private Location toLocation(LocationType locationType) {

        Location location = new Location();
        location.setCountry(locationType.getCountry());
        location.setCity(locationType.getCity());
        location.setAddress(locationType.getAddress());
        location.setSuburb(locationType.getSuburb());
        location.setPostcode(locationType.getPostcode());
        location.setLatitude(locationType.getLatitude());
        location.setLongitude(locationType.getLongitude());
        location.setCreatedAt(LocalDateTime.now());
        location.setUpdatedAt(LocalDateTime.now());

        return location;
    }

    public List<RentalSpaceResultType> toRentalSpaceResultType(List<RentalSpaceDBType> rentalSpaceResults) {

        return rentalSpaceResults.stream().map(this::toRentalSpaceResultType).toList();
    }

    public RentalSpaceResultType toRentalSpaceResultType(RentalSpaceDBType rentalSpaceResult) {

        RentalSpaceResultType rentalSpaceResultType = new RentalSpaceResultType();
        rentalSpaceResultType.setRentalImageIdentification(rentalSpaceResult.getRentalImageIdentification());
        rentalSpaceResultType.setRent(rentalSpaceResult.getRent());
        rentalSpaceResultType.setRoomType(RoomContentType.fromValue(rentalSpaceResult.getRoomType()));
        rentalSpaceResultType.setNoOfBeds(rentalSpaceResult.getNoOfBeds());
        rentalSpaceResultType.setTotalReviews(rentalSpaceResult.getTotalReviews());
        rentalSpaceResultType.setAverageReviews(rentalSpaceResult.getAverageReviews());
        rentalSpaceResultType.setRentalSpaceReference(rentalSpaceResult.getRentalSpaceReference());

        return rentalSpaceResultType;
    }

    public RetrieveRentalSpaceDetailsRespMsgType toRetrieveRentalSpaceDetailsRespMsgType(RentalSpace rentalSpace) {

        RetrieveRentalSpaceDetailsRespMsgType response = new RetrieveRentalSpaceDetailsRespMsgType();
        response.setTitle(rentalSpace.getTitle());
        response.setDescription(rentalSpace.getDescription());
        response.setRoomType(RoomContentType.fromValue(rentalSpace.getRoomType()));
        response.setNoOfBedrooms(rentalSpace.getNoOfBedrooms());
        response.setMaxNumOfPeople(rentalSpace.getMaxNumOfPeople());
        response.setNoOfBeds(rentalSpace.getNoOfBeds());
        response.setNoOfBathrooms(rentalSpace.getNoOfBathrooms());
        response.setHasLivingRoom(rentalSpace.getHasLivingRoom());
        response.setArea(rentalSpace.getArea());
        response.setSmokingAllowed(rentalSpace.getSmokingAllowed());
        response.setPetsAllowed(rentalSpace.getPetsAllowed());
        response.setEventsAllowed(rentalSpace.getEventsAllowed());
        response.setMinDuration(rentalSpace.getMinDuration());
        response.setRent(rentalSpace.getRent());
        response.setAdditionalRentPerPerson(rentalSpace.getAdditionalRentPerPerson());
        response.setAmenities(toAmenitiesType(rentalSpace.getAmenities()));
        response.getAvailableRentPeriods().addAll(toRentalSpaceDateRangeType(rentalSpace.getAvailableRentPeriods()));
        response.setAverageReviews(rentalSpace.getAverageReviews());
        response.setTotalReviews(rentalSpace.getTotalReviews());
        response.setLocation(toLocationType(rentalSpace.getLocation()));
        response.getTransportationAccess().addAll(toTransportationAccessType(rentalSpace.getTransportationAccess()));
        response.setRentalSpaceReference(rentalSpace.getRentalSpaceReference());
        response.setHost(rentalSpace.getHost().getUsername());

        return response;
    }

    private List<TransportationAccessType> toTransportationAccessType(List<TransportationAccess> transportationAccessList) {
        return transportationAccessList.stream().map(this::toTransportationAccessType).toList();
    }

    private TransportationAccessType toTransportationAccessType(TransportationAccess transportationAccess) {

        TransportationAccessType transportationAccessType = new TransportationAccessType();
        transportationAccessType.setTransportationName(transportationAccess.getTransportationName());
        transportationAccessType.setTransportationType(TransportationContentType.fromValue(transportationAccess.getTransportationType()));
        transportationAccessType.setLatitude(transportationAccess.getLatitude());
        transportationAccessType.setLongitude(transportationAccess.getLongitude());

        return transportationAccessType;
    }

    private LocationType toLocationType(Location location) {

        LocationType locationType = new LocationType();
        locationType.setCountry(location.getCountry());
        locationType.setCity(location.getCity());
        locationType.setAddress(location.getAddress());
        locationType.setSuburb(location.getSuburb());
        locationType.setPostcode(location.getPostcode());
        locationType.setLatitude(location.getLatitude());
        locationType.setLongitude(location.getLongitude());

        return locationType;
    }

    public List<RentalSpaceDateRangeType> toRentalSpaceDateRangeType(List<RentalSpaceDateRange> rentalSpaceDateRangeList) {
        return rentalSpaceDateRangeList.stream().map(this::toRentalSpaceDateRangeType).toList();
    }

    private RentalSpaceDateRangeType toRentalSpaceDateRangeType(RentalSpaceDateRange rentalSpaceDateRange) {

        RentalSpaceDateRangeType rentalSpaceDateRangeType = new RentalSpaceDateRangeType();
        rentalSpaceDateRangeType.setStartDate(DataUtils.fromLocalDateTimeToString(rentalSpaceDateRange.getStartDate()));
        rentalSpaceDateRangeType.setEndDate(DataUtils.fromLocalDateTimeToString(rentalSpaceDateRange.getEndDate()));

        return rentalSpaceDateRangeType;
    }

    private AmenitiesType toAmenitiesType(Amenities amenities) {

        AmenitiesType amenitiesType = new AmenitiesType();
        amenitiesType.setWirelessInternet(amenities.getWirelessInternet());
        amenitiesType.setCooling(amenities.getCooling());
        amenitiesType.setHeating(amenities.getHeating());
        amenitiesType.setKitchen(amenities.getKitchen());
        amenitiesType.setTelevision(amenities.getTelevision());
        amenitiesType.setParkingSpace(amenities.getParkingSpace());
        amenitiesType.setElevator(amenities.getElevator());

        return amenitiesType;
    }
}
