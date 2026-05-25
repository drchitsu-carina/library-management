package com.library.controller;

import com.library.service.BookService;
import com.library.service.RentService;
import com.library.entity.RentStatus;
import com.library.repository.BookRepository;
import com.library.repository.RentRepository; // Assuming you have this
import com.library.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/rents")
public class AdminRentController {

    private final RentService rentService;
    private final RentRepository rentRepository; // To fetch the list of all rent history
    private final UserRepository userRepository;
    private final BookService bookService;

   

    @PostMapping("/return/{id}")
    public String returnBook(@PathVariable Long id) {
        rentService.returnBook(id);
        return "redirect:/admin/rents";
    }
    
    @GetMapping
    public String listRents(Model model) {
        model.addAttribute("rents", rentRepository.findAll());
        
        // Add these lines to support the "Add Rent" Modal
        model.addAttribute("users", userRepository.findAll());
        model.addAttribute("books", bookService.getAllBooks().stream()
            .filter(b -> b.getRentStatus() == RentStatus.AVAILABLE)
            .toList());
        
        model.addAttribute("content", "admin/rent-list :: content");
        return "layout";
    }
    
   
    @GetMapping("/process")
    public String rentBook(Model model) {
        // Ensure these aren't null or causing LazyInitializationExceptions
        model.addAttribute("users", userRepository.findAll());
        model.addAttribute("books", bookService.getAllBooks());
        
        // Check if this file actually exists at src/main/resources/templates/admin/rent-process.html
        model.addAttribute("content", "admin/rent-process::content"); 
        return "layout";
    }
    

    @PostMapping("/save")
    public String saveRent(
        @RequestParam Long userId, 
        @RequestParam Long bookId,
        @RequestParam("rentDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) java.time.LocalDate rentDate,
        @RequestParam("dueDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) java.time.LocalDate dueDate) {
        
        // Pass the dates to your service method
        rentService.borrowBook(userId, bookId, rentDate, dueDate);
        
        return "redirect:/admin/rents";
    }
    
//    @PostMapping("/admin/rents/save")
//    @ResponseBody
//    public String saveRent(@RequestParam Long userId,
//                           @RequestParam Long bookId,
//                           @RequestParam String rentDate,
//                           @RequestParam String dueDate) {
//
//        rentService.createRent(userId, bookId, rentDate, dueDate);
//        return "success";
//    }
}