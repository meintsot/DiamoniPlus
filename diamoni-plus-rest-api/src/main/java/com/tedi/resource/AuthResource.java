package com.tedi.resource;

import com.tedi.auth.Roles;
import com.tedi.dto.*;
import com.tedi.fault.ValidationFault;
import com.tedi.service.AuthService;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/auth")
public class AuthResource {

    @Inject
    AuthService authService;

    @POST
    @Path("/register")
    @PermitAll
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RegisterRespMsgType register(RegisterReqMsgType param) throws ValidationFault {
        return authService.register(param);
    }

    @POST
    @Path("/login")
    @PermitAll
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public LoginRespMsgType login(LoginReqMsgType param) throws ValidationFault {
        return authService.login(param);
    }

    @POST
    @Path("/change_password")
    @RolesAllowed({Roles.ADMIN, Roles.HOST, Roles.TENANT})
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void changePassword(ChangePasswordReqMsgType param) throws ValidationFault {
        authService.changePassword(param);
    }
}
