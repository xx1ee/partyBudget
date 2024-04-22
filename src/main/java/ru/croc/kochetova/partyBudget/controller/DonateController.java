package ru.croc.kochetova.partyBudget.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.croc.kochetova.partyBudget.dto.IncomeDto;
import ru.croc.kochetova.partyBudget.service.DonateService;
import ru.croc.kochetova.partyBudget.validator.DonateValidator;


@Controller
public class DonateController {
    private final DonateService donateService;
    private final DonateValidator donateValidator;
    @Autowired
    public DonateController(DonateService donateService, DonateValidator donateValidator) {
        this.donateService = donateService;
        this.donateValidator = donateValidator;
    }
    @GetMapping("/donate")
    public String handleDonate(Model model) {
        IncomeDto incomeDto = new IncomeDto();
        model.addAttribute("income", incomeDto);
        return "donation";
    }

    @PostMapping("/donate")
    public String handleDonate(@ModelAttribute("income") @Valid IncomeDto incomeDto, BindingResult bindingResult) {
        donateValidator.validate(incomeDto, bindingResult);
        if (bindingResult.hasErrors()) {
            return "donation";
        } else {
            donateService.saveIncome(incomeDto.getAmount());
        }
        if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("PARTY_MEMBER"))) {
            return "home_member";
        } else {
            return "home_user";
        }
    }
}
