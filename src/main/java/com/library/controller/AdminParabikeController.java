package com.library.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.library.entity.Parabike;
import com.library.service.ParabikeService;

import java.util.List;

@Controller
@RequestMapping("/admin/parabikes")
@RequiredArgsConstructor
public class AdminParabikeController {

    private final ParabikeService parabikeService;

    // List Page: ပုရပိုက်အားလုံးကို ဇယားဖြင့်ပြရန်
    @GetMapping
    public String listParabikes(Model model) {
        List<Parabike> parabikes = parabikeService.getAllParabikes();
        model.addAttribute("parabikes", parabikes);
        model.addAttribute("content", "/admin/parabike-list :: content");
        return "layout";
    }
    
    

    // Add Form: အသစ်ထည့်ရန် Form စာမျက်နှာပြရန်
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("parabike", new Parabike());
        model.addAttribute("pageTitle", "Add New Manuscript");
        return "parabike/form"; // templates/parabike/form.html
    }

    // Edit Form: ပြင်ဆင်ရန် Form စာမျက်နှာပြရန်
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model, RedirectAttributes ra) {
        try {
            Parabike parabike = parabikeService.getParabikeById(id)
                    .orElseThrow(() -> new RuntimeException("Not Found"));
            model.addAttribute("parabike", parabike);
            model.addAttribute("pageTitle", "Edit Manuscript (ID: " + id + ")");
            return "parabike/form";
        } catch (RuntimeException e) {
            ra.addFlashAttribute("error", e.getMessage());
            return "redirect:/parabikes";
        }
    }

    // Save/Update: Form မှ ဒေတာများကို သိမ်းရန်
    @PostMapping("/save")
    public String saveParabike( Model model,@ModelAttribute("parabike") Parabike parabike, RedirectAttributes ra) {
        parabikeService.saveParabike(parabike);
        ra.addFlashAttribute("message", "The manuscript has been saved successfully.");
        model.addAttribute("content", "/admin/parabike-list.html :: content");
        return "layout";
    }

    // Delete: ဖျက်ရန်
    @GetMapping("/delete/{id}")
    public String deleteParabike(Model model,@PathVariable Long id, RedirectAttributes ra) {
        try {
            parabikeService.deleteParabike(id);
            ra.addFlashAttribute("message", "The manuscript ID " + id + " has been deleted.");
        } catch (Exception e) {
            ra.addFlashAttribute("error", "Error deleting: " + e.getMessage());
        }
        model.addAttribute("content", "/admin/parabike-list.html :: content");
        return "layout";
    }
}