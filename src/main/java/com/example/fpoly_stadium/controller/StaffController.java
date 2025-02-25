package com.example.fpoly_stadium.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("staff")
public class StaffController {
    @GetMapping
    public String staff() {
        return "do_thue";
    }
}
