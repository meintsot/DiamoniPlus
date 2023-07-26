package com.tedi.validator;

import com.tedi.dto.RetrieveReviewsReqMsgType;
import com.tedi.dto.SubmitReviewReqMsgType;
import com.tedi.fault.ErrorMessageType;
import com.tedi.fault.ValidationFault;
import com.tedi.model.DiamoniPlusUser;
import com.tedi.model.RentalSpace;
import com.tedi.model.RoleType;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Objects;

@ApplicationScoped
public class ReviewsValidator {

    public void validateMissingHostUsernameAndBookingReference(SubmitReviewReqMsgType param) {
        if (Objects.isNull(param.getHostUsername()) && Objects.isNull(param.getBookingReference())) {
            throw new ValidationFault(ErrorMessageType.MESS_01_ReviewsService);
        }
    }

    public void validateEitherHostUsernameOrBookingReferenceIsApplicable(SubmitReviewReqMsgType param) {
        if (Objects.nonNull(param.getHostUsername()) && Objects.nonNull(param.getBookingReference())) {
            throw new ValidationFault(ErrorMessageType.MESS_02_ReviewsService);
        }
    }

    public void validateEitherHostUsernameOrRentalSpaceReferenceIsRequired(RetrieveReviewsReqMsgType param) {
        if (Objects.nonNull(param.getUsername()) && Objects.nonNull(param.getUsername())) {
            throw new ValidationFault(ErrorMessageType.MESS_02_ReviewsService);
        }
    }
}
