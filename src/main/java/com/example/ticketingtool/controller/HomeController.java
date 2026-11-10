package com.example.ticketingtool.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping
    public String home(){
        return "Ticketing Tool API is running!";
    }

}
