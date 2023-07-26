package com.tedi.validator;

import com.tedi.fault.ErrorMessageType;
import com.tedi.fault.ValidationFault;
import com.tedi.model.DiamoniPlusUser;
import com.tedi.model.RentalSpace;
import com.tedi.model.RoleType;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RentalSpaceValidator {

    public void validateArea(Integer area) {
        if (area <= 20) {
            throw new ValidationFault(ErrorMessageType.MESS_01_RentalSpaceService);
        }
    }

    public void validateIsHostApproved(DiamoniPlusUser diamoniPlusUser) {
        if (!diamoniPlusUser.getRoleType().equals(RoleType.HOST) || !diamoniPlusUser.getHostApproved()) {
            throw new ValidationFault(ErrorMessageType.MESS_02_RentalSpaceService);
        }
    }

    public void validateIsRentalSpaceFromHost(RentalSpace rentalSpace, DiamoniPlusUser host) {
        if (!host.getRentalSpaces().contains(rentalSpace)) {
            throw new ValidationFault(ErrorMessageType.MESS_03_RentalSpaceService);
        }
    }
}
