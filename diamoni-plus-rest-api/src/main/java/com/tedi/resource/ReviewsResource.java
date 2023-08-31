package com.tedi.resource;

import com.tedi.auth.Roles;
import com.tedi.converter.ReviewsQueryParametersConverter;
import com.tedi.dto.*;
import com.tedi.fault.ValidationFault;
import com.tedi.service.ReviewsService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.UriInfo;

@Path("/reviews")
public class ReviewsResource {

    @Inject
    ReviewsService reviewsService;

    @Inject
    ReviewsQueryParametersConverter reviewsQueryParametersConverter;

    @POST
    @RolesAllowed(Roles.TENANT)
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    public void submitReview(SubmitReviewReqMsgType param) throws ValidationFault {
        reviewsService.submitReview(param);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public RetrieveReviewsRespMsgType retrieveReviews(@Context UriInfo uriInfo) throws ValidationFault {
        RetrieveReviewsReqMsgType param = reviewsQueryParametersConverter.convertRetrieveReviewsParams(uriInfo);
        return reviewsService.retrieveReviews(param);
    }

}
