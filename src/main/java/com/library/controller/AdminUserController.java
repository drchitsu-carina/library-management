package com.library.controller;

import com.library.entity.User;
import com.library.service.RentService;
import com.library.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/users")
public class AdminUserController {

    private final UserService userService;
    private final RentService rentService;

    // 👥 ADMIN USER TABLE VIEW
    @GetMapping
    public String list(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        // Assuming you have a user-list.html fragment similar to book-list.html
        model.addAttribute("content", "/admin/user-list.html :: content");
        return "layout";
    }

    // 💾 SAVE (CREATE + UPDATE)
    @PostMapping("/save")
    public String save(@ModelAttribute User user) {
        // The service should handle password encoding and field copying
        userService.saveOrUpdate(user);
        return "redirect:/admin/users";
    }

    // 🗑 DELETE
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        userService.delete(id);
        return "redirect:/admin/users";
    }

    // 🔍 API for Edit Modal (Data Attribute or AJAX approach)
    @GetMapping("/api/{id}")
    @ResponseBody
    public User getUser(@PathVariable Long id) {
        return userService.getById(id);
    }
    
    @GetMapping("/{id}/history")
    @ResponseBody
    public String userHistory(@PathVariable Long id) {
        return rentService.lastBorrowedBook(id);
    }
    
   
}