package com.tedi;

import com.tedi.auth.AuthUtils;
import com.tedi.auth.Roles;
import com.tedi.auth.UserService;
import com.tedi.dto.HelloType;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.Collections;

@Path("/hello")
public class GreetingResource {

    @Inject
    UserService userService;

    @GET
    @RolesAllowed({Roles.ADMIN, Roles.HOST})
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello " + userService.getUser();
    }

    @POST
    @PermitAll
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String createHello(HelloType helloType) {
        return AuthUtils.generateJWT(helloType.getUsername(), Collections.singletonList(helloType.getRole()));
    }
}
