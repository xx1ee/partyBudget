package ru.croc.kochetova.partyBudget.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.croc.kochetova.partyBudget.model.User;
import ru.croc.kochetova.partyBudget.service.UserDetailService;
import ru.croc.kochetova.partyBudget.validator.UserValidator;


@Controller
public class RegistrationController {
    private final UserValidator userValidator;
    private final UserDetailService userDetailService;
    @Autowired
    public RegistrationController(UserValidator userValidator, UserDetailService userDetailService) {
        this.userValidator = userValidator;
        this.userDetailService = userDetailService;
    }
    @GetMapping("/registration")
    public String handleCreateUser(Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }

    @PostMapping("/registration")
    public String createUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "registration";
        } else {
            userDetailService.save(user);
            return "redirect:/login";
        }
    }
}
