package com.tedi.mapper;

import com.tedi.dto.*;
import com.tedi.model.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.Objects;

@ApplicationScoped
public class AdminMapper {

    @Inject
    private RentalSpaceMapper rentalSpaceMapper;

    @Inject ImageFileMapper imageFileMapper;

    public ExportApplicationDataRespMsgType toExportApplicationDataRespMsgType(List<DiamoniPlusUser> diamoniPlusUsers) {
        List<DiamoniPlusUserForExportType> users = diamoniPlusUsers.stream().map(this::toDiamoniPlusUserForExportType).toList();

        ExportApplicationDataRespMsgType response = new ExportApplicationDataRespMsgType();
        response.getUsers().addAll(users);

        return response;
    }

    private DiamoniPlusUserForExportType toDiamoniPlusUserForExportType(DiamoniPlusUser diamoniPlusUser) {
        DiamoniPlusUserForExportType diamoniPlusUserForExportType = new DiamoniPlusUserForExportType();

        diamoniPlusUserForExportType.setUsername(diamoniPlusUser.getUsername());
        diamoniPlusUserForExportType.setEmail(diamoniPlusUser.getEmail());
        diamoniPlusUserForExportType.setFirstName(diamoniPlusUser.getFirstName());
        diamoniPlusUserForExportType.setLastName(diamoniPlusUser.getLastName());
        diamoniPlusUserForExportType.setRoleType(diamoniPlusUser.getRoleType().getValue());
        diamoniPlusUserForExportType.setPhone(diamoniPlusUser.getPhone());
        diamoniPlusUserForExportType.setHostApproved(diamoniPlusUser.getHostApproved());
        diamoniPlusUserForExportType.setAverageReviews(diamoniPlusUser.getAverageReviews());
        if (diamoniPlusUser.getRoleType().equals(RoleType.HOST)) {
            diamoniPlusUserForExportType.getMyRentalSpaces().addAll(
                    diamoniPlusUser.getRentalSpaces().stream().map(this::toRentalSpacesForExportType).toList()
            );
            diamoniPlusUserForExportType.getReviews().addAll(
                    diamoniPlusUser.getReviewsReceived().stream().map(this::toReviewsForExportType).toList()
            );
        }
        if (diamoniPlusUser.getRoleType().equals(RoleType.TENANT)) {
            diamoniPlusUserForExportType.getMyBookings().addAll(
                    diamoniPlusUser.getTenantedBookings().stream().map(this::toBookingsForExportType).toList()
            );
        }
        if (Objects.nonNull(diamoniPlusUser.getAvatar())) {
            diamoniPlusUserForExportType.setAvatar(imageFileMapper.toImageFileType(diamoniPlusUser.getAvatar()));
        }

        return diamoniPlusUserForExportType;
    }

    private BookingsForExportType toBookingsForExportType(Booking booking) {
        BookingsForExportType bookingsForExportType = new BookingsForExportType();

        bookingsForExportType.setBookingReference(booking.getBookingReference());
        bookingsForExportType.setHost(booking.getHost().getUsername());
        bookingsForExportType.setRentalSpaceReference(booking.getRentalSpace().getRentalSpaceReference());
        bookingsForExportType.setBookingDateRange(rentalSpaceMapper.toRentalSpaceDateRangeType(booking.getBookingDateRange()));

        return bookingsForExportType;
    }

    private RentalSpacesForExportType toRentalSpacesForExportType(RentalSpace rentalSpace) {
        RentalSpacesForExportType rentalSpacesForExportType = new RentalSpacesForExportType();

        rentalSpacesForExportType.setTitle(rentalSpace.getTitle());
        rentalSpacesForExportType.setDescription(rentalSpace.getDescription());
        rentalSpacesForExportType.setRoomType(rentalSpace.getRoomType());
        rentalSpacesForExportType.setNoOfBedrooms(rentalSpace.getNoOfBedrooms());
        rentalSpacesForExportType.setMaxNumOfPeople(rentalSpace.getMaxNumOfPeople());
        rentalSpacesForExportType.setNoOfBeds(rentalSpace.getNoOfBeds());
        rentalSpacesForExportType.setNoOfBathrooms(rentalSpace.getNoOfBathrooms());
        rentalSpacesForExportType.setHasLivingRoom(rentalSpace.getHasLivingRoom());
        rentalSpacesForExportType.setArea(rentalSpace.getArea());
        rentalSpacesForExportType.setSmokingAllowed(rentalSpace.getSmokingAllowed());
        rentalSpacesForExportType.setPetsAllowed(rentalSpace.getPetsAllowed());
        rentalSpacesForExportType.setEventsAllowed(rentalSpace.getEventsAllowed());
        rentalSpacesForExportType.setMinDuration(rentalSpace.getMinDuration());
        rentalSpacesForExportType.setRent(rentalSpace.getRent());
        rentalSpacesForExportType.setAdditionalRentPerPerson(rentalSpace.getAdditionalRentPerPerson());
        rentalSpacesForExportType.setAmenities(rentalSpaceMapper.toAmenitiesType(rentalSpace.getAmenities()));
        rentalSpacesForExportType.getAvailableRentPeriods().addAll(rentalSpaceMapper.toRentalSpaceDateRangeType(
                rentalSpace.getAvailableRentPeriods().stream().filter(RentalSpaceDateRange::getAvailable).toList()
        ));
        rentalSpacesForExportType.setAverageReviews(rentalSpace.getAverageReviews());
        rentalSpacesForExportType.setTotalReviews(rentalSpace.getTotalReviews());
        rentalSpacesForExportType.getRentalImages().addAll(
                rentalSpace.getRentalImages().stream().map(imageFileMapper::toImageFileType).toList()
        );
        rentalSpacesForExportType.setLocation(rentalSpaceMapper.toLocationType(rentalSpace.getLocation()));
        rentalSpacesForExportType.setTransportationAccess(rentalSpaceMapper.toTransportationAccessType(rentalSpace.getTransportationAccess()));
        rentalSpacesForExportType.setReviews(
                rentalSpace.getBookings().stream().filter(booking -> Objects.nonNull(booking.getReview()))
                        .map(booking -> toReviewsForExportType(booking.getReview())).toList()
        );

        return rentalSpacesForExportType;
    }

    private ReviewsForExportType toReviewsForExportType(Review review) {
        ReviewsForExportType reviewsForExportType = new ReviewsForExportType();

        reviewsForExportType.setRating(review.getRating());
        reviewsForExportType.setDescription(review.getDescription());
        reviewsForExportType.setAuthor(review.getTenant().getUsername());

        return reviewsForExportType;
    }
}
