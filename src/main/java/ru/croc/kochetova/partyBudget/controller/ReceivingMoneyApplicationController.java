package ru.croc.kochetova.partyBudget.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.croc.kochetova.partyBudget.dto.EmailForm;
import ru.croc.kochetova.partyBudget.exception.UpdateReceivingMoneyApplicationException;
import ru.croc.kochetova.partyBudget.exception.UserNotFoundException;
import ru.croc.kochetova.partyBudget.model.ReceivingMoneyApplication;
import ru.croc.kochetova.partyBudget.model.Status;
import ru.croc.kochetova.partyBudget.service.IncomeService;
import ru.croc.kochetova.partyBudget.service.ReceivingMoneyApplicationService;
import ru.croc.kochetova.partyBudget.validator.EmailFormValidator;
import ru.croc.kochetova.partyBudget.validator.ReceivingMoneyValidator;

@Controller
public class ReceivingMoneyApplicationController {
    private final ReceivingMoneyApplicationService receivingMoneyApplicationService;
    private final ReceivingMoneyValidator receivingMoneyValidator;
    @Autowired
    public ReceivingMoneyApplicationController(
            ReceivingMoneyApplicationService receivingMoneyApplicationService,
            ReceivingMoneyValidator receivingMoneyValidator) {
        this.receivingMoneyApplicationService = receivingMoneyApplicationService;
        this.receivingMoneyValidator = receivingMoneyValidator;
    }
    @GetMapping("/receivingMoneyApplication")
    public String handleReceivingMoney(Model model) {
        model.addAttribute("receivingMoneyApplication", new ReceivingMoneyApplication());
        return "receiving_money_application";
    }

    @PostMapping("/receivingMoneyApplication")
    public String handleReceivingMoney(@ModelAttribute("receivingMoneyApplication") @Valid ReceivingMoneyApplication receivingMoneyApplication, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "receiving_money_application";
        } else {
            receivingMoneyApplicationService.saveApplication(receivingMoneyApplication);
        }
        if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("PARTY_MEMBER"))) {
            return "redirect:/member";
        } else {
            return "redirect:/user";
        }
    }
    @GetMapping("/receivingMoneyApplicationRequests")
    public String handleReceivingMoneyRequests(Model model) {
        model.addAttribute("requests", receivingMoneyApplicationService.findAllUnfinishedApplications());
        return "requests_receiving_money_apps";
    }
    @PostMapping("/updateStatusReceivingMoney")
    public String handleUpdateStatusReceivingMoney(@RequestParam Integer id, @RequestParam String status) {
        var request = receivingMoneyApplicationService.findById(id).orElseThrow(UserNotFoundException::new);
        var validateResult = receivingMoneyValidator.validate(request, Status.valueOf(status));
        if (!validateResult.equals("OK")) {
            throw new UpdateReceivingMoneyApplicationException(validateResult);
        }
        receivingMoneyApplicationService.updateStatus(request, Status.valueOf(status));
        return "redirect:/receivingMoneyApplicationRequests";
    }

}
