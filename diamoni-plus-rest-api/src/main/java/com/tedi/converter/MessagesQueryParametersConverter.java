package com.tedi.converter;

import com.tedi.dto.RetrieveMessagesReqMsgType;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.UriInfo;

import java.util.Objects;

@ApplicationScoped
public class MessagesQueryParametersConverter {

    private static final Integer DEFAULT_PAGE = 1;
    private static final Integer DEFAULT_PAGE_SIZE = 10;

    public RetrieveMessagesReqMsgType convertRetrieveMessagesParams(UriInfo uriInfo) {

        MultivaluedMap<String, String> queryParameters = uriInfo.getQueryParameters();
        RetrieveMessagesReqMsgType param = new RetrieveMessagesReqMsgType();

        param.setDiscussionReference(queryParameters.getFirst(Param.DISCUSSION_REFERENCE));

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
