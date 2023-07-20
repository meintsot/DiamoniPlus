package com.tedi.auth;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.jwt.JsonWebToken;

@ApplicationScoped
public class AuthService {
    @Inject
    JsonWebToken jsonWebToken;

    public String getUser() {
        return jsonWebToken.getSubject();
    }

    public String getRole() {
        return jsonWebToken.getGroups().stream().findFirst().orElse(null);
    }
}
