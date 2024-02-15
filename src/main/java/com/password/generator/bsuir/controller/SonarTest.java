package com.password.generator.bsuir.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class SonarTest {
    @GetMapping("/sonar")
    public String sonarTest(){
        return "sssss";
    }
}
