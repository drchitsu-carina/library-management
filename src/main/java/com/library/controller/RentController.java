package com.library.controller;

import com.library.entity.User;
import com.library.entity.Rent;
import com.library.security.SecurityUtil;
import com.library.service.RentService;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/rent")
public class RentController {

    private final RentService rentService;
    private final SecurityUtil securityUtil;

    @PostMapping("/borrow")
    public String borrow(@RequestParam Long bookId) {

        Long userId = securityUtil.getCurrentUser().getId();

        rentService.borrowBook(userId, bookId);

        return "redirect:/books";
    }

    @PostMapping("/return")
    public String returnBook(@RequestParam Long rentId) {

        rentService.returnBook(rentId);
        return "redirect:/books";
    }
    
    @GetMapping("/my-rentals")
    public String showToReturnList(org.springframework.ui.Model model) {
        User currentUser = securityUtil.getCurrentUser();
        
        // Filter logic: Only get rents where status is RENTED
        // If you don't have a specific repository method, you can filter the list in the controller
        List<Rent> activeRents = rentService.getRentsByUserId(currentUser.getId())
                .stream()
                .filter(r -> "BORROWED".equals(r.getStatus().name()))
                .toList();
        
        model.addAttribute("rents", activeRents);
        model.addAttribute("content", "user/user-rent :: content");
        return "defaultLayout";
    }
}