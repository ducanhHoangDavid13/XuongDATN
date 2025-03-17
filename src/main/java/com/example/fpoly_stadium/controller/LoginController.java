package com.example.fpoly_stadium.controller;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "error", required = false) String error,
                            @RequestParam(value = "logout", required = false) String logout,
                            Model model) {
        if (error != null) {
            model.addAttribute("errorMsg", "Sai thông tin đăng nhập!");
        }
        if (logout != null) {
            model.addAttribute("msg", "Bạn đã đăng xuất thành công!");
        }
        return "login";  // Trả về file login.html
    }

    @GetMapping("/home")
    public String homePage() {
        return "home";  // Trả về file home.html
    }
}