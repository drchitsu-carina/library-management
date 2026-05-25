package com.library.controller;

import com.library.entity.Book;
import com.library.service.BookService;
import com.library.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/books")
public class AdminBookController {

    private final BookService bookService;
    private final CategoryService categoryService;

    // 📚 ADMIN TABLE VIEW
    @GetMapping
    public String list(Model model) {
        model.addAttribute("books", bookService.getAllBooks());
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("content", "/admin/book-list.html :: content");
        return "layout";
        
    }

    // 💾 SAVE (CREATE + UPDATE)
    @PostMapping("/save")
    public String save(
            @ModelAttribute Book book,
            @RequestParam(value = "file", required = false) MultipartFile file,
            @RequestParam(value = "categoryId", required = false) Long categoryId
    ) {
        bookService.saveOrUpdate(book, file, categoryId);
        return "redirect:/admin/books";
    }

    // 🗑 DELETE
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        bookService.delete(id);
        return "redirect:/admin/books";
    }
    
    // API for Edit Modal AJAX
    @GetMapping("/api/{id}")
    @ResponseBody
    public Book getBook(@PathVariable Long id) {
        return bookService.getById(id);
    }
}