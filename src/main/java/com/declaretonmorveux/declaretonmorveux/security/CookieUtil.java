package com.declaretonmorveux.declaretonmorveux.security;

import static com.declaretonmorveux.declaretonmorveux.security.jwt.JwtTokenUtil.JWT_TOKEN_VALIDITY;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseCookie;

public class CookieUtil {
    private final String cookieName = "sessionId";

    public CookieUtil() {
    }

    public ResponseCookie createCookieWithToken(String token) throws UnsupportedEncodingException {
        ResponseCookie cookie = ResponseCookie.from(cookieName, URLEncoder.encode("Bearer "+ token, "UTF-8"))
        .maxAge((int)JWT_TOKEN_VALIDITY * 1000)
        .sameSite("None")
        .secure(true)
        .path("/")
        .httpOnly(true)
        .build();

        // Cookie sessionIdCookie = new Cookie(cookieName, URLEncoder.encode("Bearer "+ token, "UTF-8"));
        // sessionIdCookie.setHttpOnly(true);
        // sessionIdCookie.setSecure(true);
        // sessionIdCookie.setPath("/");
        // sessionIdCookie.setMaxAge((int)JWT_TOKEN_VALIDITY * 1000);
        // System.err.println(cookie.toString());
        // response.addCookie((Cookie)cookie);
        
        return cookie;
    }

    public String getCookieName() {
        return cookieName;
    }
   
}
