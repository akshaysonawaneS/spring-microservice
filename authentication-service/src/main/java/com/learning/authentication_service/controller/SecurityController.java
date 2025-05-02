package com.learning.authentication_service.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecurityController {

    @GetMapping("/test")
    @PreAuthorize("hasRole('ADMIN')")
    public String test(){
        return "hello world";
    }
}
