package com.tedi.resource;

import com.tedi.auth.Roles;
import com.tedi.converter.BookingQueryParametersConverter;
import com.tedi.dto.ConfirmBookingReqMsgType;
import com.tedi.dto.MyBookingsReqMsgType;
import com.tedi.dto.MyBookingsRespMsgType;
import com.tedi.fault.ValidationFault;
import com.tedi.service.BookingService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.UriInfo;

@Path("/booking")
public class BookingResource {

    @Inject
    BookingService bookingService;

    @Inject
    BookingQueryParametersConverter bookingQueryParametersConverter;

    @POST
    @RolesAllowed(Roles.TENANT)
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    public void confirmBooking(ConfirmBookingReqMsgType param) throws ValidationFault {
        bookingService.confirmBooking(param);
    }

    @DELETE
    @Path("/{bookingReference}")
    @RolesAllowed(Roles.TENANT)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public void cancelBooking(@PathParam("bookingReference") String bookingReference) throws ValidationFault {
        bookingService.cancelBooking(bookingReference);
    }

    @GET
    @RolesAllowed(Roles.TENANT)
    @Produces(MediaType.APPLICATION_JSON)
    public MyBookingsRespMsgType myBookings(@Context UriInfo uriInfo) throws ValidationFault {
        MyBookingsReqMsgType param = bookingQueryParametersConverter.convertMyBookingsParams(uriInfo);
        return bookingService.myBookings(param);
    }

}
