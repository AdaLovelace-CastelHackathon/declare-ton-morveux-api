package com.declaretonmorveux.declaretonmorveux.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.declaretonmorveux.declaretonmorveux.security.jwt.JwtRequestFilter;
import com.declaretonmorveux.declaretonmorveux.service.ExpiredJwtService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
public class CustomLogoutHandler implements LogoutHandler {

    @Autowired
    ExpiredJwtService expiredJwtService;

    @Autowired
    CookieUtil cookieUtil;

    @Autowired
    JwtRequestFilter jwtRequestFilter;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String token = null;

        try {
            token = jwtRequestFilter.getTokenFromCookies(request);
        } catch (Exception e) {
            System.err.println("Cannot get Token from cookies");
            System.err.println(e);
        }

        if (token == null) {
            token = request.getHeader("Authorization");
        }

        if (token != null && token.startsWith("Bearer ")) {
            ResponseCookie expiredCookie = null;
            token = token.substring(7);

            try {
                expiredCookie = cookieUtil.createCookieWithToken(token, 0);
            } catch (Exception e) {
                System.err.println("Cannot create Cookie with Token");
                System.err.println(e);
            }


            if (expiredCookie != null) {
                token = token.substring(7);
                expiredJwtService.addExpiredJwt(token);
                
                response.setHeader("Set-Cookie", expiredCookie.toString());
            }

        }

    }

}
