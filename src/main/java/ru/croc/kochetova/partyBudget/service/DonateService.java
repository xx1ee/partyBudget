package ru.croc.kochetova.partyBudget.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.croc.kochetova.partyBudget.model.Income;
import ru.croc.kochetova.partyBudget.model.Role;
import ru.croc.kochetova.partyBudget.model.User;
import ru.croc.kochetova.partyBudget.repository.DonateRepository;
import ru.croc.kochetova.partyBudget.repository.RoleRepository;
import ru.croc.kochetova.partyBudget.repository.UserRepository;

import java.math.BigDecimal;


@Service
public class DonateService {
    private final DonateRepository donateRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    @Autowired
    public DonateService(DonateRepository donateRepository, UserRepository userRepository, RoleRepository roleRepository) {
        this.donateRepository = donateRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }
    @Transactional
    public void saveIncome(BigDecimal amount) {
        Role role = roleRepository.findFirstByAuthority("ADMIN");
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmailIgnoreCase(email).get();
        user.setWallet_amount(user.getWallet_amount().subtract(amount));
        userRepository.save(user);
        User admin = userRepository.findFirstByAuthority(role).get();
        admin.setWallet_amount(admin.getWallet_amount().add(amount));
        userRepository.save(admin);
        donateRepository.save(new Income(amount, user));
    }
}
