package com.tedi.converter;

import com.tedi.dto.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.UriInfo;

import java.util.Objects;

@ApplicationScoped
public class AdminQueryParametersConverter {

    private static final Integer DEFAULT_PAGE = 1;
    private static final Integer DEFAULT_PAGE_SIZE = 10;
    private static final String TRUE = "true";

    public SearchUserProfilesReqMsgType convertSearchUserProfilesParams(UriInfo uriInfo) {

        MultivaluedMap<String, String> queryParameters = uriInfo.getQueryParameters();
        SearchUserProfilesReqMsgType param = new SearchUserProfilesReqMsgType();

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

        param.setUsername(queryParameters.getFirst(Param.USERNAME));
        param.setEmail(queryParameters.getFirst(Param.EMAIL));
        param.setFirstName(queryParameters.getFirst(Param.FIRST_NAME));
        param.setLastName(queryParameters.getFirst(Param.LAST_NAME));
        param.setPhone(queryParameters.getFirst(Param.PHONE));
        param.setRoleType(queryParameters.getFirst(Param.ROLE_TYPE));

        if (Objects.nonNull(queryParameters.getFirst(Param.AVERAGE_REVIEWS))) {
            param.setAverageReviews(Double.parseDouble(queryParameters.getFirst(Param.AVERAGE_REVIEWS)));
        }

        if (Objects.nonNull(queryParameters.getFirst(Param.IS_HOST_APPROVED))) {
            param.setIsHostApproved(TRUE.equals(queryParameters.getFirst(Param.IS_HOST_APPROVED)));
        }

        return param;
    }
}
