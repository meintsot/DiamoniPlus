package com.tedi.converter;

import com.tedi.dto.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.UriInfo;

import java.util.Objects;

@ApplicationScoped
public class ReviewsQueryParametersConverter {

    private static final Integer DEFAULT_PAGE = 1;
    private static final Integer DEFAULT_PAGE_SIZE = 10;

    public RetrieveReviewsReqMsgType convertRetrieveReviewsParams(UriInfo uriInfo) {

        RetrieveReviewsReqMsgType param = new RetrieveReviewsReqMsgType();
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
