package ru.croc.kochetova.partyBudget.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.croc.kochetova.partyBudget.model.ReceivingMoneyApplication;
import ru.croc.kochetova.partyBudget.model.Role;
import ru.croc.kochetova.partyBudget.model.Status;
import ru.croc.kochetova.partyBudget.model.User;
import ru.croc.kochetova.partyBudget.repository.ReceivingMoneyApplicationRepository;
import ru.croc.kochetova.partyBudget.repository.RoleRepository;
import ru.croc.kochetova.partyBudget.repository.UserRepository;

import java.util.NoSuchElementException;

@Component
public class ReceivingMoneyValidator {
    UserRepository userRepository;
    RoleRepository roleRepository;
    @Autowired
    public ReceivingMoneyValidator(UserRepository userRepository,
                                   RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }
    public String validate(ReceivingMoneyApplication receivingMoneyApplication, Status status) {
        Role role = roleRepository.findFirstByAuthority("ADMIN");
        User admin = userRepository.findFirstByAuthority(role).orElseThrow(NoSuchElementException::new);
        if (status == Status.COMPLETED) {
            return (receivingMoneyApplication.getAmount().compareTo(admin.getWallet_amount()) > 0) ? "Запрашиваемая сумма превышает бюджет партии" : "OK";
        }
        return "OK";
    }
}
