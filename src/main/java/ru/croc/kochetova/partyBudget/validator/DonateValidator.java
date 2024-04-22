package ru.croc.kochetova.partyBudget.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import ru.croc.kochetova.partyBudget.dto.IncomeDto;
import ru.croc.kochetova.partyBudget.model.User;
import ru.croc.kochetova.partyBudget.repository.UserRepository;

@Component
public class DonateValidator {
    UserRepository userRepository;
    @Autowired
    public DonateValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public void validate(IncomeDto incomeDto, Errors errors) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmailIgnoreCase(name).get();
        if ((incomeDto.getAmount().compareTo(user.getWallet_amount()) > 0)) {
            errors.rejectValue("amount", "","Сумма не должна превышать ваш баланс");
        }
    }
}
