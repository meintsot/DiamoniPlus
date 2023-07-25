package com.tedi.fault;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ValidationFaultMapper implements ExceptionMapper<ValidationFault> {

    @Override
    public Response toResponse(ValidationFault validationFault) {
        String errorMessage = validationFault.getMessage();
        JsonObject errorJson = Json.createObjectBuilder()
                .add("fault", errorMessage)
                .build();

        return Response.status(Response.Status.BAD_REQUEST)
                .entity(errorJson)
                .status(Response.Status.BAD_REQUEST)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
