package com.mode.util;

import java.util.Date;

import com.mode.base.BaseConfig;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * A utility class for dealing with creating and parsing JWT access tokens.
 */
public final class TokenHandler {

    /**
     * Signing Key for JWT token encryption.
     */
    private static final String JWT_SIGNING_KEY = "hello, this is higuowai.com.";

    /**
     * Generate an access token using the given signing key from this user.
     * @param username
     * @return
     */
    public static String createTokenForUser(String username) {
        final long currentTimeMillis = System.currentTimeMillis();
        final long expireTimeMillis = currentTimeMillis + BaseConfig.TOKEN_EXPIRE_WINDOW;

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(currentTimeMillis))
                .setExpiration(new Date(expireTimeMillis))
                .signWith(SignatureAlgorithm.HS512, JWT_SIGNING_KEY)
                .compact();
    }

    /**
     * Decode the given access token and return the username.
     * @param token
     * @return
     */
    public static String parseUserFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(JWT_SIGNING_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}