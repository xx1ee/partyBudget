package ru.croc.kochetova.partyBudget.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.croc.kochetova.partyBudget.model.User;
import ru.croc.kochetova.partyBudget.repository.UserRepository;

@Controller
public class WalletController {
    private final UserRepository userRepository;
    @Autowired
    public WalletController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @GetMapping("/wallet")
    public String wallet(Model model) {
        User user = userRepository.findByEmailIgnoreCase(SecurityContextHolder.getContext().getAuthentication().getName()).get();
        model.addAttribute("user", user);
        return "wallet";
    }
}
