package com.tedi.service;

import com.tedi.auth.UserService;
import com.tedi.dao.BookingDao;
import com.tedi.dao.DiamoniPlusUserDao;
import com.tedi.dao.RentalSpaceDao;
import com.tedi.dao.ReviewsDao;
import com.tedi.dto.*;
import com.tedi.fault.ErrorMessageType;
import com.tedi.fault.ValidationFault;
import com.tedi.mapper.ReviewsMapper;
import com.tedi.model.Booking;
import com.tedi.model.DiamoniPlusUser;
import com.tedi.model.RentalSpace;
import com.tedi.model.Review;
import com.tedi.utils.DataUtils;
import com.tedi.validator.ReviewsValidator;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.Objects;

@ApplicationScoped
public class ReviewsService {

    @Inject
    UserService userService;

    @Inject
    ReviewsValidator reviewsValidator;

    @Inject
    DiamoniPlusUserDao diamoniPlusUserDao;

    @Inject
    RentalSpaceDao rentalSpaceDao;

    @Inject
    BookingDao bookingDao;

    @Inject
    ReviewsMapper reviewsMapper;

    @Inject
    ReviewsDao reviewsDao;

//    TODO add exception(s) for submitting only one review for a rental space or a user
    public void submitReview(SubmitReviewReqMsgType param) throws ValidationFault {

        reviewsValidator.validateMissingHostUsernameAndBookingReference(param);
        reviewsValidator.validateEitherHostUsernameOrBookingReferenceIsApplicable(param);

        DiamoniPlusUser tenant = diamoniPlusUserDao.findByUsername(userService.getUser()).orElseThrow(
                () -> new ValidationFault(ErrorMessageType.DATA_01_ReviewsService)
        );

        Review review = reviewsMapper.toReview(param);

        if (Objects.nonNull(param.getHostUsername())) {
            submitUserReview(param.getHostUsername(), review, tenant);
        } else {
            submitBookingReview(param.getBookingReference(), review, tenant);
        }
    }

    private void submitBookingReview(String bookingReference, Review review, DiamoniPlusUser tenant) throws ValidationFault {

        Booking booking = bookingDao.findByBookingReference(bookingReference).orElseThrow(
                () -> new ValidationFault(ErrorMessageType.DATA_03_ReviewsService)
        );

        review.setBooking(booking);
        review.setTenant(tenant);
        RentalSpace rentalSpace = booking.getRentalSpace();
        rentalSpace.setAverageReviews(DataUtils.calculateAverage(rentalSpace.getAverageReviews(), review.getRating(), rentalSpace.getTotalReviews()));
        rentalSpace.setTotalReviews(rentalSpace.getTotalReviews() + 1);
        reviewsDao.saveReview(review);
    }

    private void submitUserReview(String hostUsername, Review review, DiamoniPlusUser tenant) throws ValidationFault {

        DiamoniPlusUser host = diamoniPlusUserDao.findByUsername(hostUsername).orElseThrow(
                () -> new ValidationFault(ErrorMessageType.DATA_02_ReviewsService)
        );

        host.setAverageReviews(DataUtils.calculateAverage(host.getAverageReviews(), review.getRating(), host.getTotalReviews()));
        host.setTotalReviews(host.getTotalReviews() + 1);

        review.setHost(host);
        review.setTenant(tenant);
        reviewsDao.saveReview(review);
    }

    public RetrieveReviewsRespMsgType retrieveReviews(RetrieveReviewsReqMsgType param) throws ValidationFault {

        reviewsValidator.validateEitherHostUsernameOrRentalSpaceReferenceIsRequired(param);
        List<Review> reviews = reviewsDao.retrieveReviews(param);

        RetrieveReviewsRespMsgType response = reviewsMapper.toRetrieveReviewsRespMsgType(reviews);

        if (Objects.nonNull(param.getUsername())) {
            DiamoniPlusUser diamoniPlusUser = diamoniPlusUserDao.findByUsername(param.getUsername()).orElseThrow(
                    () -> new ValidationFault(ErrorMessageType.DATA_02_ReviewsService)
            );
            response.setAverageReviews(diamoniPlusUser.getAverageReviews());
        } else {
            RentalSpace rentalSpace = rentalSpaceDao.retrieveRentalSpaceDetails(param.getRentalSpaceReference()).orElseThrow(
                    () -> new ValidationFault(ErrorMessageType.DATA_04_ReviewsService)
            );
            response.setAverageReviews(rentalSpace.getAverageReviews());
        }

        return response;
    }
}
