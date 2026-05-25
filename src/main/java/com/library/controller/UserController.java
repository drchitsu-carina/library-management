package com.library.controller;

import com.library.entity.User;
import com.library.repository.UserRepository;
import com.library.security.SecurityUtil;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final SecurityUtil securityUtil;
    private final PasswordEncoder passwordEncoder;

    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    
    @GetMapping("/profile")
    public String showProfile(Model model) {
        // Get currently logged-in user
        User currentUser = securityUtil.getCurrentUser();
        
        model.addAttribute("user", currentUser);
        model.addAttribute("content", "/user/user-profile :: content");
        return "defaultLayout";
    }
    
    
    @PostMapping("/profile/update")
    @ResponseBody
    public ResponseEntity<String> updateProfile(@RequestParam String name, 
                                                @RequestParam String phone,
                                                @RequestParam String building,
                                                @RequestParam String address) {
        try {
            User currentUser = securityUtil.getCurrentUser();
            currentUser.setName(name);
            currentUser.setPhone(phone);
            currentUser.setBuilding(building);
            currentUser.setAddress(address);
            
            userRepository.save(currentUser);
            return ResponseEntity.ok("Profile updated successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating profile");
        }
    }
    
    
    @PostMapping("/profile/change-password")
    @ResponseBody
    public ResponseEntity<String> changePassword(@RequestParam String oldPassword, 
                                                @RequestParam String newPassword) {
        User currentUser = securityUtil.getCurrentUser();

        // 1. Check if the old password matches
        if (!passwordEncoder.matches(oldPassword, currentUser.getPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Current password is incorrect");
        }

        // 2. Update to new encoded password
        currentUser.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(currentUser);

        return ResponseEntity.ok("Password updated successfully!");
    }
    
    
    
}