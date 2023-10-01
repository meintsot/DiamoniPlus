package com.tedi.validator;

import com.tedi.fault.ErrorMessageType;
import com.tedi.fault.ValidationFault;
import com.tedi.model.RoleType;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ApplicationScoped
public class AuthValidator {

    public void validateUsername(String username) throws ValidationFault {
        if (username.length() < 5) {
            throw new ValidationFault(ErrorMessageType.MESS_01_AuthService);
        }
    }

    public void validatePassword(String password, String confirmPassword) throws ValidationFault {
        if (password.length() < 5) {
            throw new ValidationFault(ErrorMessageType.MESS_02_AuthService);
        }

        if (!Objects.equals(password, confirmPassword)) {
            throw new ValidationFault(ErrorMessageType.MESS_03_AuthService);
        }
    }

    public void validateEmail(String email) throws ValidationFault {
        String emailPattern = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(email);

        if (!matcher.matches()) {
            throw new ValidationFault(ErrorMessageType.MESS_04_AuthService);
        }
    }

    public void validateNoAdmin(String role) {
        if (RoleType.ADMIN.getValue().equals(role)) {
            throw new ValidationFault(ErrorMessageType.MESS_05_AuthService);
        }
    }
}
