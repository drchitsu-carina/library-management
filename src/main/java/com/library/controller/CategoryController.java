package com.library.controller;

import com.library.entity.Category;
import com.library.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/categories")
public class CategoryController {

    private final CategoryService categoryService;

    // 📋 List all categories
    @GetMapping
    public String list(Model model) {
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("content", "/admin/category-list.html :: content");
        return "layout";
        
    }

    // ➕ Show create form
    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("category", new Category());
        model.addAttribute("content", "/admin/category-form.html :: content");
        return "layout";
        
    }

    // 💾 Save category (create + update)
    @PostMapping("/save")
    public String save(@ModelAttribute Category category) {
        categoryService.save(category);
        return "redirect:/admin/categories";
    }

    // ✏️ Edit form
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("category", categoryService.getById(id));
        
        return "admin/category-form";
    }

    // 🗑 Delete category
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        categoryService.delete(id);
       
        return "redirect:/admin/categories";
    }
}