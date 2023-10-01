package com.tedi.resource;

import com.tedi.auth.Roles;
import com.tedi.converter.MessagesQueryParametersConverter;
import com.tedi.dto.*;
import com.tedi.fault.ValidationFault;
import com.tedi.service.DiscussionsService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.UriInfo;

@Path("/discussions")
public class DiscussionsResource {

    @Inject
    MessagesQueryParametersConverter messagesQueryParametersConverter;

    @Inject
    DiscussionsService discussionsService;

    @POST
    @RolesAllowed(Roles.TENANT)
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public CreateAndRetrieveDiscussionRespMsgType createAndRetrieveDiscussion(CreateAndRetrieveDiscussionReqMsgType param) {
        return discussionsService.createAndRetrieveDiscussion(param);
    }

    @GET
    @Path("/users")
    @Produces(MediaType.APPLICATION_JSON)
    public RetrieveDiscussionsRespMsgType retrieveDiscussions() throws ValidationFault {
        return discussionsService.retrieveDiscussions();
    }

    @POST
    @Path("/messages")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public void saveMessage(SaveMessageReqMsgType param) {
        this.discussionsService.saveMessage(param);
    }

    @GET
    @Path("/messages")
    @Produces(MediaType.APPLICATION_JSON)
    public RetrieveMessagesRespMsgType retrieveMessages(@Context UriInfo uriInfo) throws ValidationFault {
        RetrieveMessagesReqMsgType param = messagesQueryParametersConverter.convertRetrieveMessagesParams(uriInfo);
        return discussionsService.retrieveMessages(param);
    }

    @DELETE
    @RolesAllowed(Roles.HOST)
    @Path("/messages/{messageId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public void deleteMessage(@PathParam("messageId") String messageId) {
        discussionsService.deleteMessage(messageId);
    }
}
