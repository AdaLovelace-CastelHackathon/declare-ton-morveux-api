package com.declaretonmorveux.declaretonmorveux.controller;

import static com.declaretonmorveux.declaretonmorveux.security.jwt.JwtTokenUtil.JWT_TOKEN_VALIDITY;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.declaretonmorveux.declaretonmorveux.model.Parent;
import com.declaretonmorveux.declaretonmorveux.security.CookieUtil;
import com.declaretonmorveux.declaretonmorveux.security.jwt.JwtRequest;
import com.declaretonmorveux.declaretonmorveux.security.jwt.JwtResponse;
import com.declaretonmorveux.declaretonmorveux.security.jwt.JwtTokenUtil;
import com.declaretonmorveux.declaretonmorveux.service.ParentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JwtAuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private ParentService parentService;

    /**
     * Authenticate a user with a given JwtRequest
     * 
     * @param authenticationRequest A JwtRequest DTO
     * @see JwtRequest
     * 
     * @return A response entiry depending on the request treatment
     * @throws Exception
     */
    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest,
            HttpServletResponse response) throws Exception {

        Authentication authentication = authenticate(authenticationRequest.getUsername(),
                authenticationRequest.getPassword());
                
        UserDetails userDetails = null;
        String token = null;

        if (authentication.isAuthenticated()) {
            try {
                userDetails = parentService.loadUserByUsername(authenticationRequest.getUsername());
            } catch (UsernameNotFoundException e) {
                throw new UsernameNotFoundException("USERNAME NOT FOUND AFTER AUTHENTICATION");
            }
        }

        if (userDetails != null) {
            CookieUtil cookieUtil = new CookieUtil();
            HttpHeaders headers = new HttpHeaders();

            token = jwtTokenUtil.generateToken(userDetails);
            headers.add("Set-Cookie",
                    cookieUtil.createCookieWithToken(token, (int) JWT_TOKEN_VALIDITY * 1000).toString());

            return ResponseEntity.ok().headers(headers).body(new JwtResponse(token));
        }

        return ResponseEntity.status(409).body("Token cannot be generated");
    }

    /**
     * Register the user
     * 
     * @param user An ApplicationUser object fullfiled by the requestor
     * @see ApplicationUser
     * 
     * @return A response entiry depending on the request treatment
     * @throws Exception
     */
    @PostMapping("/register")
    public ResponseEntity<?> createUser(@Valid @RequestBody Parent user) throws Exception {

        boolean isUserAdded = parentService.addNewUser(user);

        if (!isUserAdded) {
            return ResponseEntity.badRequest().body("This username is already used");
        }

        try {
            final UserDetails userDetails = parentService.loadUserByUsername(user.getUsername());
            final String token = jwtTokenUtil.generateToken(userDetails);

            return ResponseEntity.ok(new JwtResponse(token));
        } catch (Exception e) {

            // todo: send failure cause
            System.err.println(e);
            return ResponseEntity.unprocessableEntity().build();
        }
    }

    /**
     * Check if the user is authenticated
     * 
     * @param request        A HttpServletRequest object that contain the current
     *                       request
     * @param authentication An Authentication object that contains user's
     *                       authentication details
     * 
     * @return A boolean depending on the user's authentication
     */
    @GetMapping("/isAuthenticated")
    public boolean isAuthenticated(HttpServletRequest request, Authentication authentication) {
        return authentication != null ? authentication.isAuthenticated() : false;
    }

    /**
     * Authenticate a user
     * 
     * @param username
     * @param password
     * @return an Authentication object
     * @throws Exception
     */
    private Authentication authenticate(String username, String password) throws Exception {

        try {
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

}