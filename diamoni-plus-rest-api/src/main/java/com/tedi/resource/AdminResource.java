package com.tedi.resource;

import com.tedi.auth.Roles;
import com.tedi.converter.AdminQueryParametersConverter;
import com.tedi.dto.*;
import com.tedi.fault.ValidationFault;
import com.tedi.service.AdminService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.UriInfo;

@Path("/admin")
public class AdminResource {

    @Inject
    AdminService adminService;

    @Inject
    AdminQueryParametersConverter adminQueryParametersConverter;

    @POST
    @Path("/host/{username}")
    @RolesAllowed(Roles.ADMIN)
    @Transactional
    public void approveHostRole(@PathParam("username") String username) {
        adminService.approveHostRole(username);
    }

    @GET
    @Path("/host/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public RetrieveHostApprovalRespMsgType retrieveHostApproval(
            @PathParam("username") String username
    ) throws ValidationFault {
        return adminService.retrieveHostApproval(username);
    }

    @GET
    @Path("/users")
    @RolesAllowed(Roles.ADMIN)
    @Produces(MediaType.APPLICATION_JSON)
    public SearchUserProfilesRespMsgType searchUserProfiles(@Context UriInfo uriInfo) throws ValidationFault {
        SearchUserProfilesReqMsgType param = adminQueryParametersConverter.convertSearchUserProfilesParams(uriInfo);
        return adminService.searchUserProfiles(param);
    }

    @POST
    @Path("/export")
    @RolesAllowed(Roles.ADMIN)
    @Produces(MediaType.APPLICATION_JSON)
    public ExportApplicationDataRespMsgType exportApplicationData() {
        return adminService.exportApplicationData();
    }
}
