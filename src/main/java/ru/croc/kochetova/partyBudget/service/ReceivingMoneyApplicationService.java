package ru.croc.kochetova.partyBudget.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.croc.kochetova.partyBudget.model.*;
import ru.croc.kochetova.partyBudget.repository.ExpenseRepository;
import ru.croc.kochetova.partyBudget.repository.ReceivingMoneyApplicationRepository;
import ru.croc.kochetova.partyBudget.repository.RoleRepository;
import ru.croc.kochetova.partyBudget.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ReceivingMoneyApplicationService {
    private static final List<Status> stats = List.of(Status.CREATED, Status.CONSIDERED);
    private final ReceivingMoneyApplicationRepository receivingMoneyApplicationRepository;
    private final UserRepository userRepository;
    private final ExpenseRepository expenseRepository;
    private final RoleRepository roleRepository;
    @Autowired
    public ReceivingMoneyApplicationService(ReceivingMoneyApplicationRepository receivingMoneyApplicationRepository,
                                            UserRepository userRepository, ExpenseRepository expenseRepository,
                                            RoleRepository roleRepository) {
        this.receivingMoneyApplicationRepository = receivingMoneyApplicationRepository;
        this.userRepository = userRepository;
        this.expenseRepository = expenseRepository;
        this.roleRepository = roleRepository;
    }
    public void saveApplication(ReceivingMoneyApplication receivingMoneyApplication) {
        User userFind = userRepository.findByEmailIgnoreCase(SecurityContextHolder.getContext().getAuthentication().getName()).get();
        receivingMoneyApplication.setStatus(Status.CREATED);
        receivingMoneyApplication.setUser(userFind);
        receivingMoneyApplicationRepository.save(receivingMoneyApplication);
    }

    public List<ReceivingMoneyApplication> findAllUnfinishedApplications() {
        return receivingMoneyApplicationRepository.findAllByStatusIn(stats);
    }
    @Transactional
    public void updateStatus(ReceivingMoneyApplication request, Status status) {
        request.setStatus(status);
        if (status == Status.COMPLETED) {
            Role role = roleRepository.findFirstByAuthority("ADMIN");
            User admin = userRepository.findFirstByAuthority(role).get();
            admin.setWallet_amount(admin.getWallet_amount().subtract(request.getAmount()));
            userRepository.save(admin);
            User user = request.getUser();
            user.setWallet_amount(user.getWallet_amount().add(request.getAmount()));
            userRepository.save(user);
            expenseRepository.save(new Expense(request.getAmount(), request.getTarget(), request.getUser()));
        }
        receivingMoneyApplicationRepository.save(request);
    }
    public Optional<ReceivingMoneyApplication> findById(Integer id) {
        return receivingMoneyApplicationRepository.findById(id);
    }
}
