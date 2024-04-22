package ru.croc.kochetova.partyBudget.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.croc.kochetova.partyBudget.dto.EmailForm;
import ru.croc.kochetova.partyBudget.service.IncomeService;
import ru.croc.kochetova.partyBudget.validator.EmailFormValidator;

@Controller
public class IncomesController {
    private final IncomeService incomeService;
    private final EmailFormValidator emailFormValidator;

    @Autowired
    public IncomesController(IncomeService incomeService, EmailFormValidator emailFormValidator) {
        this.incomeService = incomeService;
        this.emailFormValidator = emailFormValidator;
    }

    @GetMapping("/findIncomes")
    public String handleFindIncomes(Model model) {
        model.addAttribute("emailForm", new EmailForm());
        return "enter_email_to_find_incomes_from_user";
    }
    @PostMapping("/findIncomes")
    public String handleFindIncomesFromUser(@ModelAttribute EmailForm emailForm, Model model, BindingResult bindingResult) {
        emailFormValidator.validate(emailForm, bindingResult);
        if (bindingResult.hasErrors()) {
            return "enter_email_to_find_incomes_from_user";
        } else {
            model.addAttribute("incomes", incomeService.findByEmail(emailForm.getEmail()));
            return "all_incomes_from_user";
        }
    }
}
