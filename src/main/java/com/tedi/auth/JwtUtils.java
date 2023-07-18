package com.tedi.auth;

import io.smallrye.jwt.build.Jwt;

import java.util.HashSet;
import java.util.List;

public class JwtUtils {

    public static String generateJWT(String username, List<String> roles) {
        return Jwt.issuer("https://diamoniplus.com/issuer")
                .subject(username)
                .upn("tedi@quarkus.io")
                .groups(new HashSet<>(roles))
                .sign();
    }

}
