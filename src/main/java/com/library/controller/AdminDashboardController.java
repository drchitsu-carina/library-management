package com.library.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.library.repository.UserRepository;
import com.library.service.BookService;
import com.library.service.CategoryService;
import com.library.service.FavouriteService;
import com.library.service.RentService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor // Add this for constructor injection
public class AdminDashboardController {

    private final BookService bookService;
    private final CategoryService categoryService;
    private final UserRepository userRepository;
    private final RentService rentService;
    private final FavouriteService favouriteService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        // Fetching real counts from the database
        model.addAttribute("bookCount", bookService.getAllBooks().size());
        model.addAttribute("categoryCount", categoryService.getAllCategories().size());
        model.addAttribute("userCount", userRepository.count());
        model.addAttribute("rentCount", rentService.getActiveRentCount()); // You may need to add this method to your service
        model.addAttribute("favCount", favouriteService.getTotalFavourites()); 

        model.addAttribute("content", "admin/dashboard :: content");
        return "layout";
    }
}