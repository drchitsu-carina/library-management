package com.library.controller;

import com.library.entity.User;
import com.library.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    // 🔐 Login page
    @GetMapping("/login")
    public String loginPage() {
        return "user/login";
    }
    
 

    // 📝 Register page
    @GetMapping("/register")
    public String registerPage() {
        return "user/register";
    }

    // 📝 Handle register
    @PostMapping("/register")
    public String register(User user) {
        userService.register(user);
        return "redirect:/login";
    }

}