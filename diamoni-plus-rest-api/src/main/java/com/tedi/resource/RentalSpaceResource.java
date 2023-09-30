package com.tedi.resource;

import com.tedi.auth.Roles;
import com.tedi.converter.RentalSpaceQueryParametersConverter;
import com.tedi.dto.*;
import com.tedi.fault.ValidationFault;
import com.tedi.service.RentalSpaceService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.UriInfo;

@Path("/rental-spaces")
public class RentalSpaceResource {

    @Inject
    RentalSpaceService rentalSpaceService;

    @Inject
    RentalSpaceQueryParametersConverter rentalSpaceQueryParametersConverter;

    @POST
    @RolesAllowed(Roles.HOST)
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public CreateRentalSpaceRespMsgType createRentalSpace(CreateRentalSpaceReqMsgType param) throws ValidationFault {
        return rentalSpaceService.createRentalSpace(param);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public SearchRentalSpacesRespMsgType searchRentalSpaces(@Context UriInfo uriInfo) throws ValidationFault {
        SearchRentalSpacesReqMsgType param = rentalSpaceQueryParametersConverter.convertSearchRentalSpacesParams(uriInfo);
        return rentalSpaceService.searchRentalSpaces(param);
    }

    @GET
    @Path("/{rentalSpaceReference}")
    @Produces(MediaType.APPLICATION_JSON)
    public RetrieveRentalSpaceDetailsRespMsgType retrieveRentalSpaceDetails(
            @PathParam("rentalSpaceReference") String rentalSpaceReference
    ) throws ValidationFault {
        return rentalSpaceService.retrieveRentalSpaceDetails(rentalSpaceReference);
    }

    @PUT
    @RolesAllowed(Roles.HOST)
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public void updateRentalSpaceDetails(UpdateRentalSpaceDetailsReqMsgType param) throws ValidationFault {
        rentalSpaceService.updateRentalSpaceDetails(param);
    }

    @GET
    @Path("/{rentalSpaceReference}/images/{binaryIdentification}")
    @Produces(MediaType.APPLICATION_JSON)
    public RetrieveRentalImageRespMsgType retrieveRentalImage(
            @PathParam("rentalSpaceReference") String rentalSpaceReference,
            @PathParam("binaryIdentification") String binaryIdentification
    ) throws ValidationFault {
        return rentalSpaceService.retrieveRentalImage(rentalSpaceReference, binaryIdentification);
    }

    @POST
    @Path("/images")
    @RolesAllowed(Roles.HOST)
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public UploadRentalImageRespMsgType uploadRentalImage(UploadRentalImageReqMsgType param) throws ValidationFault {
        return rentalSpaceService.uploadRentalImage(param);
    }

    @DELETE
    @Path("/images/{binaryIdentification}")
    @RolesAllowed(Roles.HOST)
    @Transactional
    public void deleteRentalImage(
            @PathParam("binaryIdentification") String binaryIdentification
    ) throws ValidationFault {
        rentalSpaceService.deleteRentalImage(binaryIdentification);
    }

    @GET
    @Path("/host")
    @RolesAllowed(Roles.HOST)
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public MyRentalSpacesRespMsgType myRentalSpaces(@Context UriInfo uriInfo) throws ValidationFault {
        MyRentalSpacesReqMsgType param = rentalSpaceQueryParametersConverter.convertMyRentalSpacesParams(uriInfo);
        return rentalSpaceService.myRentalSpaces(param);
    }
}
