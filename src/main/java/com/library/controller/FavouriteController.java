package com.library.controller;

import com.library.entity.Book;
import com.library.entity.Favourite;
import com.library.security.SecurityUtil;
import com.library.service.FavouriteService;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller // Changed to RestController for AJAX support
@RequiredArgsConstructor
@RequestMapping("/favourite")
public class FavouriteController {

    private final FavouriteService favouriteService;
    private final SecurityUtil securityUtil;

//    @PostMapping("/toggle")
//    @ResponseBody
//    public String toggle(@RequestParam Long bookId) {
//        Long userId = securityUtil.getCurrentUser().getId();
//        
//        // Logic: If exists, remove. If not, add. 
//        if(favouriteService.isExist(userId, bookId))
//        	favouriteService.removeFavourite(userId,bookId);
//        // Assuming your service has a toggle or add method:
//        else
//        	favouriteService.addFavourite(userId, bookId); 
//        
//        return "Success";
//    }
    
    @PostMapping("/toggle")
    public ResponseEntity<String> toggle(@RequestParam Long bookId) {
        Long userId = securityUtil.getCurrentUser().getId();
        
        if (favouriteService.isExist(userId, bookId)) {
            favouriteService.removeFavourite(userId, bookId);
            return ResponseEntity.ok("REMOVED");
        } else {
            favouriteService.addFavourite(userId, bookId);
            return ResponseEntity.ok("ADDED");
        }
    }
    
    @PostMapping("/add")
    @ResponseBody // This tells Spring NOT to look for a HTML page/redirect
    public ResponseEntity<String> add(@RequestParam Long bookId) {
        try {
            Long userId = securityUtil.getCurrentUser().getId();
            favouriteService.addFavourite(userId, bookId);
            return ResponseEntity.ok("Success");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error");
        }
    }
    
    @PostMapping("/remove")
    @ResponseBody // This tells Spring NOT to look for a HTML page/redirect
    public ResponseEntity<String> remove(@RequestParam Long bookId) {
        try {
            Long userId = securityUtil.getCurrentUser().getId();
            favouriteService.removeFavourite(userId, bookId);
            return ResponseEntity.ok("Success");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error");
        }
    }
    
    @GetMapping("/list")
    public String listFavourites(Model model) {
        Long userId = securityUtil.getCurrentUser().getId();
        
        // Get the list of books favorited by the user
        List<Favourite> favList = favouriteService.getUserFavourites(userId);
        
        List<Book> favBooks=new ArrayList<Book>();
        for (Favourite fav : favList) {
			favBooks.add(fav.getBook());
		}
        
        // We also need the IDs to keep the heart icons red on this page
        List<Long> favIds = favBooks.stream()
                                    .map(Book::getId)
                                    .collect(Collectors.toList());

        model.addAttribute("books", favBooks);
        model.addAttribute("favouriteBookIds", favIds);
        model.addAttribute("content", "/user/fav-list :: content");
//        model.addAttribute("content", "home :: content");
        return "defaultLayout";
        
    }
}