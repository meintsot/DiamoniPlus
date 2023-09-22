package com.tedi.resource;

import com.tedi.dto.RetrieveUserProfileImageRespMsgType;
import com.tedi.service.ProfileService;
import com.tedi.auth.Roles;
import com.tedi.dto.GetUserProfileRespMsgType;
import com.tedi.dto.UpdateUserProfileReqMsgType;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

@Path("/profiles")
public class ProfileResource {

    @Inject
    private ProfileService profileService;

    @GET
    @Path("/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public GetUserProfileRespMsgType getUserProfile(@PathParam("username") String username) {
        return profileService.getUserProfile(username);
    }

    @GET
    @Path("/{username}/image")
    @Produces(MediaType.APPLICATION_JSON)
    public RetrieveUserProfileImageRespMsgType retrieveUserProfileImage(@PathParam("username") String username) {
        return profileService.retrieveUserProfileImage(username);
    }

    @PUT
    @RolesAllowed({Roles.HOST, Roles.TENANT})
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public void updateUserProfile(UpdateUserProfileReqMsgType param) {
        profileService.updateUserProfile(param);
    }
}