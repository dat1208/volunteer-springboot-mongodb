package com.volunteer.springbootmongo.controller;


import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/hello")
public class HelloColtroller {
    @GetMapping()
    public String hello(){
        return "Hello World";
    }
}
