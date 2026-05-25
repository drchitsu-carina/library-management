package com.library.controller;

import com.library.entity.Book;
import com.library.entity.Favourite;
import com.library.entity.Parabike;
import com.library.security.SecurityUtil;
import com.library.service.BookService;
import com.library.service.FavouriteService;
import com.library.service.ParabikeService;

import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequiredArgsConstructor
@RequestMapping("/") // Change this from "/books" to "/"
public class BookController {

    private final BookService bookService;
    private final SecurityUtil securityUtil;
    private final FavouriteService favouriteService;
    private final ParabikeService parabikeService;

//    @GetMapping("/") // This handles the landing page
//    public String home(Model model) {
//        model.addAttribute("books", bookService.getAllBooks());
//        return "home"; // This must match the filename src/main/resources/templates/home.html
//    }
    
    @GetMapping("/")
    public String landingPage(Model model) {
        model.addAttribute("books", bookService.getAllBooks());
        model.addAttribute("content", "home :: content"); // Points to home.html fragment
        return "defaultLayout"; // Use the public layout
    }

//    @GetMapping("/books") // <--- Does this match the URL you are typing?
//    public String listBooks(Model model) {
//    	model.addAttribute("books", bookService.getAllBooks());
//        model.addAttribute("content", "home :: content"); // Points to home.html fragment
//        return "defaultLayout"; // Use the public layout
//    }
    
    @GetMapping("/books")
    public String listBooks(Model model) {
        model.addAttribute("books", bookService.getAllBooks());
        
        // Fetch Favorite IDs for the logged-in user
        if (securityUtil.getCurrentUser() != null) {
            Long userId = securityUtil.getCurrentUser().getId();
            // Assuming your service has this method to return List<Long>
            List<Favourite> favList = favouriteService.getUserFavourites(userId);
            List<Long> favIds=new ArrayList<Long>();
            
            for (Favourite fav : favList) {
				favIds.add(fav.getBook().getId());
			}
            model.addAttribute("favouriteBookIds", favIds);
        }

        model.addAttribute("content", "home :: content");
        return "defaultLayout";
    }
    
    @GetMapping("/books/details/{id}")
    public String bookDetails(Model model, @PathVariable Long id) {
        Book book = bookService.getById(id);
        
        model.addAttribute("book", book);
        // Point this to your new detail fragment file
        model.addAttribute("content", "book-detail :: content"); 
        return "defaultLayout";
    }
    
    @GetMapping("/contact")
    public String showContact(Model model) {
    	model.addAttribute("content", "contact :: content");
        return "defaultLayout";
    	
    }
    
 // List Page: ပုရပိုက်အားလုံးကို ဇယားဖြင့်ပြရန်
    @GetMapping("/parabike")
    public String listParabikes(Model model) {
        List<Parabike> parabikes = parabikeService.getAllParabikes();
        model.addAttribute("parabikes", parabikes);
        model.addAttribute("content", "parabike :: content");
        return "defaultLayout";
    }
    
    public String getMethodName(@RequestParam String param) {
        return new String();
    }
    
}