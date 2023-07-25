package com.tedi.auth;

import io.smallrye.jwt.build.Jwt;
import org.mindrot.jbcrypt.BCrypt;

import java.util.HashSet;
import java.util.List;

public class AuthUtils {

    public static String generateJWT(String username, List<String> roles) {
        return Jwt.issuer("https://diamoniplus.com/issuer")
                .subject(username)
                .upn("tedi@quarkus.io")
                .groups(new HashSet<>(roles))
                .sign();
    }

    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public static boolean passwordsMatch(String password, String hashPassword) {
        return BCrypt.checkpw(password, hashPassword);
    }

}
