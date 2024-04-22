package ru.croc.kochetova.partyBudget.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.croc.kochetova.partyBudget.dto.EmailForm;
import ru.croc.kochetova.partyBudget.service.ExpenseService;
import ru.croc.kochetova.partyBudget.validator.EmailFormValidator;

@Controller
public class ExpenseController {
    private final ExpenseService expenseService;
    private final EmailFormValidator emailFormValidator;
    @Autowired
    public ExpenseController(ExpenseService expenseService, EmailFormValidator emailFormValidator) {
        this.expenseService = expenseService;
        this.emailFormValidator = emailFormValidator;
    }

    @GetMapping("/findExpenses")
    public String handleFindExpenses(Model model) {
        model.addAttribute("emailForm", new EmailForm());
        return "enter_email_to_find_expenses_to_user";
    }
    @PostMapping("/findExpenses")
    public String handleFindExpensesToUser(@ModelAttribute EmailForm emailForm, Model model, BindingResult bindingResult) {
        emailFormValidator.validate(emailForm, bindingResult);
        if (bindingResult.hasErrors()) {
            return "enter_email_to_find_expenses_to_user";
        } else {
            model.addAttribute("expenses", expenseService.findByEmail(emailForm.getEmail()));
            return "all_expenses_to_user";
        }
    }

}
