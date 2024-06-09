package com.Twitter.org.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ContentController {

    // Available to all users
    @GetMapping("/home")
    public String handleWelcome() {
        return "home";
    }

    // Available to authenticated users only, for testing purposes
    @GetMapping("/user/home")
    public String handleUserHome() {
        return "home_user";
    }

}
