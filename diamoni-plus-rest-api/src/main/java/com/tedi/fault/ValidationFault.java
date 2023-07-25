package com.tedi.fault;

import jakarta.ws.rs.WebApplicationException;

public class ValidationFault extends WebApplicationException {

    public ValidationFault(ErrorMessageType errorMessageType) {
        super(errorMessageType.getError());
    }
}
