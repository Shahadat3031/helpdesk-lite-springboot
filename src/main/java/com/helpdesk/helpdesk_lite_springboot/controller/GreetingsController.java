package com.helpdesk.helpdesk_lite_springboot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingsController {

    @GetMapping("/")
    public String sayHello(){
        return "Hello";
    }

}
