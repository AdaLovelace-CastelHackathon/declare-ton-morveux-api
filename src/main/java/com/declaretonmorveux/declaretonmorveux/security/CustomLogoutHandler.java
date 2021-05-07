package com.declaretonmorveux.declaretonmorveux.security;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.declaretonmorveux.declaretonmorveux.service.ExpiredJwtService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
public class CustomLogoutHandler implements LogoutHandler{

    @Autowired
    ExpiredJwtService expiredJwtService;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String token = null;
        Cookie[] cookies = request.getCookies();

        if(cookies != null){
            for(Cookie cookie : cookies){
                if(cookie.getName().equals("sessionId")){
                    token = cookie.getValue();
                }
            }
        } else {
            token = request.getHeader("Authorization");
        }

        if(token != null && token.startsWith("Bearer ")){
            token = token.substring(7);
            expiredJwtService.addExpiredJwt(token);
        }

    }
    
}
