package ru.croc.kochetova.partyBudget.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.croc.kochetova.partyBudget.model.Expense;
import ru.croc.kochetova.partyBudget.repository.ExpenseRepository;
import ru.croc.kochetova.partyBudget.repository.UserRepository;

import java.util.List;

@Service
public class ExpenseService {
    ExpenseRepository expenseRepository;
    UserRepository userRepository;
    @Autowired
    public ExpenseService(ExpenseRepository expenseRepository, UserRepository userRepository) {
        this.expenseRepository = expenseRepository;
        this.userRepository = userRepository;
    }
    public List<Expense> findByEmail(String email) {
        return expenseRepository.findExpenseByUser(userRepository.findByEmailIgnoreCase(email).get());
    }
}
