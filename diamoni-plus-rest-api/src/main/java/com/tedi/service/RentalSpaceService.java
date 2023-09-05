package com.tedi.service;

import com.tedi.auth.UserService;
import com.tedi.dao.DiamoniPlusUserDao;
import com.tedi.dao.RentalSpaceDao;
import com.tedi.dto.*;
import com.tedi.fault.ErrorMessageType;
import com.tedi.fault.ValidationFault;
import com.tedi.mapper.ImageFileMapper;
import com.tedi.mapper.RentalSpaceMapper;
import com.tedi.model.*;
import com.tedi.validator.RentalSpaceValidator;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@ApplicationScoped
public class RentalSpaceService {

    @Inject
    UserService userService;

    @Inject
    RentalSpaceValidator rentalSpaceValidator;

    @Inject
    DiamoniPlusUserDao diamoniPlusUserDao;

    @Inject
    RentalSpaceDao rentalSpaceDao;

    @Inject
    RentalSpaceMapper rentalSpaceMapper;

    @Inject
    ImageFileMapper imageFileMapper;

    public CreateRentalSpaceRespMsgType createRentalSpace(CreateRentalSpaceReqMsgType param) throws ValidationFault {

        rentalSpaceValidator.validateArea(param.getArea());

        DiamoniPlusUser host = diamoniPlusUserDao.findByUsername(userService.getUser()).orElseThrow(
                () -> new ValidationFault(ErrorMessageType.DATA_01_RentalSpaceService)
        );

        rentalSpaceValidator.validateIsHostApproved(host);

        RentalSpace rentalSpace = rentalSpaceMapper.toRentalSpace(param);
        List<ImageFile> rentalImages = rentalSpaceDao.retrieveRentalImages(param.getRentalImageIdentifications());
        rentalSpaceValidator.validateAtLeastOneRentalImageIsRequired(rentalImages);
        rentalSpace.getRentalImages().addAll(rentalImages);
        rentalSpace.setHost(host);
        host.getRentalSpaces().add(rentalSpace);
        rentalSpaceDao.saveRentalSpace(rentalSpace);

        CreateRentalSpaceRespMsgType response = new CreateRentalSpaceRespMsgType();
        response.setRentalSpaceReference(rentalSpace.getRentalSpaceReference());

        return response;
    }

    public SearchRentalSpacesRespMsgType searchRentalSpaces(SearchRentalSpacesReqMsgType param) throws ValidationFault {

        List<RentalSpaceDBType> rentalSpaceResults = rentalSpaceDao.searchRentalSpaces(param);
        Long totalResults = rentalSpaceDao.countRentalSpaces(param);

        List<RentalSpaceResultType> rentalSpaceResultTypes = rentalSpaceMapper.toRentalSpaceResultType(rentalSpaceResults);

        SearchRentalSpacesRespMsgType response = new SearchRentalSpacesRespMsgType();
        response.setRentalSpaceResults(rentalSpaceResultTypes);
        response.setTotalResults(totalResults);

        return response;
    }

    public RetrieveRentalSpaceDetailsRespMsgType retrieveRentalSpaceDetails(String rentalSpaceReference) {

        RentalSpace rentalSpace = rentalSpaceDao.retrieveRentalSpaceDetails(rentalSpaceReference).orElseThrow(
                () -> new ValidationFault(ErrorMessageType.DATA_02_RentalSpaceService)
        );
        return rentalSpaceMapper.toRetrieveRentalSpaceDetailsRespMsgType(rentalSpace);
    }

    public void updateRentalSpaceDetails(UpdateRentalSpaceDetailsReqMsgType param) throws ValidationFault {

        rentalSpaceValidator.validateArea(param.getArea());

        DiamoniPlusUser host = diamoniPlusUserDao.findByUsername(userService.getUser()).orElseThrow(
                () -> new ValidationFault(ErrorMessageType.DATA_01_RentalSpaceService)
        );

        rentalSpaceValidator.validateIsHostApproved(host);

        RentalSpace rentalSpace = rentalSpaceDao.retrieveRentalSpaceDetails(param.getRentalSpaceReference()).orElseThrow(
                () -> new ValidationFault(ErrorMessageType.DATA_02_RentalSpaceService)
        );

        rentalSpaceValidator.validateIsRentalSpaceFromHost(rentalSpace, host);

        if (Objects.nonNull(param.getTitle())) {
            rentalSpace.setTitle(param.getTitle());
        }
        if (Objects.nonNull(param.getDescription())) {
            rentalSpace.setDescription(param.getDescription());
        }
        if (Objects.nonNull(param.getRoomType())) {
            rentalSpace.setRoomType(param.getRoomType().getValue());
        }
        if (Objects.nonNull(param.getNoOfBedrooms())) {
            rentalSpace.setNoOfBedrooms(param.getNoOfBedrooms());
        }
        if (Objects.nonNull(param.getMaxNumOfPeople())) {
            rentalSpace.setMaxNumOfPeople(param.getMaxNumOfPeople());
        }
        if (Objects.nonNull(param.getNoOfBeds())) {
            rentalSpace.setNoOfBeds(param.getNoOfBeds());
        }
        if (Objects.nonNull(param.getNoOfBathrooms())) {
            rentalSpace.setNoOfBathrooms(param.getNoOfBathrooms());
        }
        if (Objects.nonNull(param.getHasLivingRoom())) {
            rentalSpace.setHasLivingRoom(param.getHasLivingRoom());
        }
        if (Objects.nonNull(param.getArea())) {
            rentalSpace.setArea(param.getArea());
        }
        if (Objects.nonNull(param.getSmokingAllowed())) {
            rentalSpace.setSmokingAllowed(param.getSmokingAllowed());
        }
        if (Objects.nonNull(param.getPetsAllowed())) {
            rentalSpace.setPetsAllowed(param.getPetsAllowed());
        }
        if (Objects.nonNull(param.getEventsAllowed())) {
            rentalSpace.setEventsAllowed(param.getEventsAllowed());
        }
        if (Objects.nonNull(param.getMinDuration())) {
            rentalSpace.setMinDuration(param.getMinDuration());
        }
        if (Objects.nonNull(param.getRent())) {
            rentalSpace.setRent(param.getRent());
        }
        if (Objects.nonNull(param.getAdditionalRentPerPerson())) {
            rentalSpace.setAdditionalRentPerPerson(param.getAdditionalRentPerPerson());
        }
        if (Objects.nonNull(param.getAmenities())) {
            AmenitiesType amenitiesType = param.getAmenities();
            Amenities amenities = rentalSpace.getAmenities();
            if (Objects.nonNull(amenities.getWirelessInternet())) {
                amenities.setWirelessInternet(amenitiesType.getWirelessInternet());
            }
            if (Objects.nonNull(amenities.getCooling())) {
                amenities.setCooling(amenitiesType.getCooling());
            }
            if (Objects.nonNull(amenities.getHeating())) {
                amenities.setHeating(amenitiesType.getHeating());
            }
            if (Objects.nonNull(amenities.getKitchen())) {
                amenities.setKitchen(amenitiesType.getKitchen());
            }
            if (Objects.nonNull(amenities.getTelevision())) {
                amenities.setTelevision(amenitiesType.getTelevision());
            }
            if (Objects.nonNull(amenities.getParkingSpace())) {
                amenities.setParkingSpace(amenitiesType.getParkingSpace());
            }
            if (Objects.nonNull(amenities.getElevator())) {
                amenities.setElevator(amenitiesType.getElevator());
            }
            rentalSpace.setAmenities(amenities);
        }
        if (param.getAvailableRentPeriods().size() != 0) {
            rentalSpace.getAvailableRentPeriods().clear();
            rentalSpace.getAvailableRentPeriods().addAll(rentalSpaceMapper.toRentalSpaceDateRange(param.getAvailableRentPeriods()));
        }
        if (Objects.nonNull(param.getLocation())) {
            LocationType locationType = param.getLocation();
            Location location = rentalSpace.getLocation();
            if (Objects.nonNull(locationType.getCountry())) {
                location.setCountry(locationType.getCountry());
            }
            if (Objects.nonNull(locationType.getCity())) {
                location.setCity(locationType.getCity());
            }
            if (Objects.nonNull(locationType.getAddress())) {
                location.setAddress(locationType.getAddress());
            }
            if (Objects.nonNull(locationType.getSuburb())) {
                location.setSuburb(locationType.getSuburb());
            }
            if (Objects.nonNull(locationType.getPostcode())) {
                location.setPostcode(locationType.getPostcode());
            }
            if (Objects.nonNull(locationType.getLatitude())) {
                location.setLatitude(locationType.getLatitude());
            }
            if (Objects.nonNull(locationType.getLongitude())) {
                location.setLongitude(locationType.getLongitude());
            }
            rentalSpace.setLocation(location);
        }
        if (param.getTransportationAccess().size() != 0) {
            rentalSpace.getTransportationAccess().clear();
            rentalSpace.getTransportationAccess().addAll(rentalSpaceMapper.toTransportationAccess(param.getTransportationAccess()));
        }
        rentalSpace.setUpdatedAt(LocalDateTime.now());
    }

    public RetrieveRentalImageRespMsgType retrieveRentalImage(String rentalSpaceReference, String binaryIdentification) throws ValidationFault {

        ImageFile rentalImage = rentalSpaceDao.retrieveRentalImage(rentalSpaceReference, binaryIdentification).orElseThrow(
                () -> new ValidationFault(ErrorMessageType.DATA_03_RentalSpaceService)
        );

        RetrieveRentalImageRespMsgType response = new RetrieveRentalImageRespMsgType();
        response.setRentalImage(imageFileMapper.toImageFileType(rentalImage));
        return response;
    }

    public UploadRentalImageRespMsgType uploadRentalImage(UploadRentalImageReqMsgType param) throws ValidationFault {

        DiamoniPlusUser host = diamoniPlusUserDao.findByUsername(userService.getUser()).orElseThrow(
                () -> new ValidationFault(ErrorMessageType.DATA_01_RentalSpaceService)
        );

        rentalSpaceValidator.validateIsHostApproved(host);

        ImageFile rentalImage = imageFileMapper.toImageFile(param.getRentalImage());

        rentalSpaceDao.saveRentalImage(rentalImage);

        UploadRentalImageRespMsgType response = new UploadRentalImageRespMsgType();
        response.setBinaryIdentification(rentalImage.getBinaryIdentification());
        return response;
    }

    public void deleteRentalImage(String binaryIdentification) throws ValidationFault {

        boolean success = rentalSpaceDao.deleteRentalImage(binaryIdentification);
        if (!success) {
            throw new ValidationFault(ErrorMessageType.DATA_03_RentalSpaceService);
        }
    }

    public MyRentalSpacesRespMsgType myRentalSpaces(MyRentalSpacesReqMsgType param) {

        List<RentalSpaceDBType> rentalSpaceResults = rentalSpaceDao.myRentalSpaces(param, userService.getUser());
        Long totalResults = rentalSpaceDao.countMyRentalSpaces(param, userService.getUser());

        List<RentalSpaceResultType> rentalSpaceResultTypes = rentalSpaceMapper.toRentalSpaceResultType(rentalSpaceResults);

        MyRentalSpacesRespMsgType response = new MyRentalSpacesRespMsgType();
        response.setRentalSpaceResults(rentalSpaceResultTypes);
        response.setTotalResults(totalResults);

        return response;
    }
}
