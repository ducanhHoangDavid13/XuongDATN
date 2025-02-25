package com.example.fpoly_stadium.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/home")
public class HomeController {

    @GetMapping("/")
    public String showHomePage(Model model) {
        model.addAttribute("message", "Chào mừng đến với Đặt Sân Sóng 24/7!");
        return "home";
    }
}
