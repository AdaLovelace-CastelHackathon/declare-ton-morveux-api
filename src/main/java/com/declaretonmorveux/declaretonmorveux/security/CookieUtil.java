package com.declaretonmorveux.declaretonmorveux.security;

import static com.declaretonmorveux.declaretonmorveux.security.jwt.JwtTokenUtil.JWT_TOKEN_VALIDITY;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

@Component
public class CookieUtil {
    private final String cookieTokenName = "sessionId";

    public CookieUtil() {
    }

    public ResponseCookie createCookieWithToken(String token, int exp) throws UnsupportedEncodingException {
        ResponseCookie cookie = ResponseCookie.from(cookieTokenName, URLEncoder.encode("Bearer "+ token, "UTF-8"))
        .maxAge(exp)
        .sameSite("None")
        .secure(true)
        .path("/")
        .httpOnly(true)
        .build();
        
        return cookie;
    }

    public String getCookieTokenName() {
        return cookieTokenName;
    }
   
}
