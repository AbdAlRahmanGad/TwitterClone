package com.Twitter.org.controllers;

import com.Twitter.org.Models.LoginForm;
import com.Twitter.org.security.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {
    private final JwtTokenUtil jwtTokenUtil;
    private final UserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthenticationController(JwtTokenUtil jwtTokenUtil, UserDetailsService userDetailsService, AuthenticationManager authenticationManager) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationManager;
    }


    @PostMapping("/authenticate")
    // authenticates the user, and returns a JWT token if the authentication is successful.
    // the token is used to authenticate the user in subsequent requests
    public String authenticateAndGetToken(@RequestBody LoginForm loginForm) {

        // authenticate the user with the username and password from the login form
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        loginForm.username(),
                        loginForm.password()
                ));

        // Generate a token if the user is authenticated
        if (authentication.isAuthenticated()) {
            return jwtTokenUtil.generateToken(userDetailsService.loadUserByUsername(loginForm.username()));
        } else {
            return "Authentication failed";
        }
    }
}
