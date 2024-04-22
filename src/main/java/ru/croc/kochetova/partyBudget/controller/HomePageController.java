package ru.croc.kochetova.partyBudget.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomePageController {
    @GetMapping("/admin")
    public String handleAdminHome() {
        return "home_admin";
    }
    @GetMapping("/user")
    public String handleUserHome() {
        return "home_user";
    }
    @GetMapping("/member")
    public String handleMemberHome() {
        return "home_member";
    }
    @RequestMapping("/")
    public String mainPage() {
        return "redirect:/registration";
    }
}
