package me.project.bankingsystem.controller;

import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test/")
public class TestController {

    @GetMapping("user")
    public String user() {
        return "PAGE FOR ROLE USER";
    }

    @GetMapping("admin")
    public String admin() {
        return "PAGE FOR ROLE ADMIN";
    }
}
