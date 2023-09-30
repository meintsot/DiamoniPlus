package com.tedi.service;

import com.tedi.auth.UserService;
import com.tedi.dao.BookingDao;
import com.tedi.dao.DiamoniPlusUserDao;
import com.tedi.dao.RentalSpaceDao;
import com.tedi.dto.*;
import com.tedi.fault.ErrorMessageType;
import com.tedi.fault.ValidationFault;
import com.tedi.mapper.RentalSpaceMapper;
import com.tedi.model.*;
import com.tedi.utils.DataUtils;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class BookingService {

    @Inject
    UserService userService;

    @Inject
    DiamoniPlusUserDao diamoniPlusUserDao;

    @Inject
    BookingDao bookingDao;

    @Inject
    RentalSpaceDao rentalSpaceDao;

    @Inject
    RentalSpaceMapper rentalSpaceMapper;

    public void confirmBooking(ConfirmBookingReqMsgType param) throws ValidationFault {

        DiamoniPlusUser tenant = diamoniPlusUserDao.findByUsername(userService.getUser()).orElseThrow(
                () -> new ValidationFault(ErrorMessageType.DATA_01_BookingService)
        );

        RentalSpace rentalSpace = rentalSpaceDao.retrieveRentalSpaceDetails(param.getRentalSpaceReference()).orElseThrow(
                () -> new ValidationFault(ErrorMessageType.DATA_02_BookingService)
        );

        RentalSpaceDateRangeType bookingRange = param.getBookingRange();

        RentalSpaceDateRange rentalSpaceDateRange = rentalSpace.getAvailableRentPeriods().stream()
                .filter(dateRange -> DataUtils.areDateRangesEqual(bookingRange, dateRange))
                .findFirst().orElseThrow(
                        () -> new ValidationFault(ErrorMessageType.DATA_03_BookingService)
                );
        rentalSpaceDateRange.setAvailable(false);

        Booking booking = new Booking();
        booking.setBookingReference(UUID.randomUUID().toString());
        booking.setHost(rentalSpace.getHost());
        booking.setTenant(tenant);
        booking.setRentalSpace(rentalSpace);
        booking.setBookingDateRange(rentalSpaceDateRange);
        booking.setCreatedAt(LocalDateTime.now());
        booking.setUpdatedAt(LocalDateTime.now());
        bookingDao.saveBooking(booking);
    }

    public void cancelBooking(String bookingReference) throws ValidationFault {
        Booking booking = bookingDao.findByBookingReference(bookingReference).orElseThrow(
                () -> new ValidationFault(ErrorMessageType.DATA_04_BookingService)
        );
        booking.getBookingDateRange().setAvailable(true);
        Review review = booking.getReview();
        if (review != null) {
            review.setBooking(null);
        }
        bookingDao.cancelBooking(booking);
    }

    public MyBookingsRespMsgType myBookings(MyBookingsReqMsgType param) throws ValidationFault {
        List<RentalSpaceDBType> rentalSpaceResults = bookingDao.myBookings(param, userService.getUser());
        Long totalResults = bookingDao.countMyBookings(param, userService.getUser());

        List<RentalSpaceResultType> rentalSpaceResultTypes = rentalSpaceMapper.toRentalSpaceResultType(rentalSpaceResults);

        MyBookingsRespMsgType response = new MyBookingsRespMsgType();
        response.setRentalSpaceResults(rentalSpaceResultTypes);
        response.setTotalResults(totalResults);

        return response;
    }
}
