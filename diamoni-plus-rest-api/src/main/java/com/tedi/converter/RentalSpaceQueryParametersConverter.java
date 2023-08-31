package com.tedi.converter;

import com.tedi.dto.*;
import com.tedi.utils.DataUtils;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.UriInfo;

import java.util.Objects;

@ApplicationScoped
public class RentalSpaceQueryParametersConverter {

    private static final Integer DEFAULT_PAGE = 1;
    private static final Integer DEFAULT_PAGE_SIZE = 10;

    private static final String TRUE = "1";

    public SearchRentalSpacesReqMsgType convertSearchRentalSpacesParams(UriInfo uriInfo) {

        MultivaluedMap<String, String> queryParameters = uriInfo.getQueryParameters();
        SearchRentalSpacesReqMsgType param = new SearchRentalSpacesReqMsgType();
        LocationType location = new LocationType();
        AmenitiesType amenities = new AmenitiesType();

        location.setCountry(queryParameters.getFirst(Param.COUNTRY));
        location.setCity(queryParameters.getFirst(Param.CITY));
        location.setAddress(queryParameters.getFirst(Param.ADDRESS));
        location.setSuburb(queryParameters.getFirst(Param.SUBURB));
        location.setPostcode(queryParameters.getFirst(Param.POSTCODE));

        if (Objects.nonNull(queryParameters.getFirst(Param.LATITUDE))) {
            location.setLatitude(Double.parseDouble(queryParameters.getFirst(Param.LATITUDE)));
        }

        if (Objects.nonNull(queryParameters.getFirst(Param.LONGITUDE))) {
            location.setLongitude(Double.parseDouble(queryParameters.getFirst(Param.LONGITUDE)));
        }

        param.setLocation(location);

        if (Objects.nonNull(queryParameters.getFirst(Param.START_DATE))) {
            param.setStartDate(DataUtils.fromStringToLocalDateTime(queryParameters.getFirst(Param.START_DATE)));
        }

        if (Objects.nonNull(queryParameters.getFirst(Param.END_DATE))) {
            param.setEndDate(DataUtils.fromStringToLocalDateTime(queryParameters.getFirst(Param.END_DATE)));
        }

        if (Objects.nonNull(queryParameters.getFirst(Param.MAX_NUM_OF_PEOPLE))) {
            param.setMaxNumOfPeople(Integer.valueOf(queryParameters.getFirst(Param.MAX_NUM_OF_PEOPLE)));
        }

        if (Objects.nonNull(queryParameters.getFirst(Param.PAGE))) {
            param.setPage(Integer.valueOf(queryParameters.getFirst(Param.PAGE)));
        } else {
            param.setPage(DEFAULT_PAGE);
        }

        if (Objects.nonNull(queryParameters.getFirst(Param.PAGE_SIZE))) {
            param.setPageSize(Integer.valueOf(queryParameters.getFirst(Param.PAGE_SIZE)));
        } else {
            param.setPageSize(DEFAULT_PAGE_SIZE);
        }

        if (Objects.nonNull(queryParameters.getFirst(Param.ROOM_TYPE))) {
            param.setRoomType(RoomContentType.fromValue(queryParameters.getFirst(Param.ROOM_TYPE)));
        }

        if (Objects.nonNull(queryParameters.getFirst(Param.MAXIMUM_COST))) {
            param.setMaximumCost(Integer.valueOf(queryParameters.getFirst(Param.MAXIMUM_COST)));
        }

        if (Objects.nonNull(queryParameters.getFirst(Param.WIRELESS_INTERNET))) {
            amenities.setWirelessInternet(TRUE.equals(queryParameters.getFirst(Param.WIRELESS_INTERNET)));
        }

        if (Objects.nonNull(queryParameters.getFirst(Param.COOLING))) {
            amenities.setCooling(TRUE.equals(queryParameters.getFirst(Param.COOLING)));
        }

        if (Objects.nonNull(queryParameters.getFirst(Param.HEATING))) {
            amenities.setHeating(TRUE.equals(queryParameters.getFirst(Param.HEATING)));
        }

        if (Objects.nonNull(queryParameters.getFirst(Param.KITCHEN))) {
            amenities.setKitchen(TRUE.equals(queryParameters.getFirst(Param.KITCHEN)));
        }

        if (Objects.nonNull(queryParameters.getFirst(Param.TELEVISION))) {
            amenities.setTelevision(TRUE.equals(queryParameters.getFirst(Param.TELEVISION)));
        }

        if (Objects.nonNull(queryParameters.getFirst(Param.PARKING_SPACE))) {
            amenities.setParkingSpace(TRUE.equals(queryParameters.getFirst(Param.PARKING_SPACE)));
        }

        if (Objects.nonNull(queryParameters.getFirst(Param.ELEVATOR))) {
            amenities.setElevator(TRUE.equals(queryParameters.getFirst(Param.ELEVATOR)));
        }

        param.setAmenities(amenities);

        return param;
    }

    public MyRentalSpacesReqMsgType convertMyRentalSpacesParams(UriInfo uriInfo) {

        MyRentalSpacesReqMsgType param = new MyRentalSpacesReqMsgType();
        MultivaluedMap<String, String> queryParameters = uriInfo.getQueryParameters();

        if (Objects.nonNull(queryParameters.getFirst(Param.PAGE))) {
            param.setPage(Integer.valueOf(queryParameters.getFirst(Param.PAGE)));
        } else {
            param.setPage(DEFAULT_PAGE);
        }

        if (Objects.nonNull(queryParameters.getFirst(Param.PAGE_SIZE))) {
            param.setPageSize(Integer.valueOf(queryParameters.getFirst(Param.PAGE_SIZE)));
        } else {
            param.setPageSize(DEFAULT_PAGE_SIZE);
        }

        return param;
    }
}
