package ru.croc.kochetova.partyBudget.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.croc.kochetova.partyBudget.model.Income;
import ru.croc.kochetova.partyBudget.repository.IncomeRepository;
import ru.croc.kochetova.partyBudget.repository.UserRepository;

import java.util.List;

@Service
public class IncomeService {
    IncomeRepository incomeRepository;
    UserRepository userRepository;
    @Autowired
    public IncomeService(IncomeRepository incomeRepository, UserRepository userRepository) {
        this.incomeRepository = incomeRepository;
        this.userRepository = userRepository;
    }
    public List<Income> findByEmail(String email) {
        return incomeRepository.findIncomesByUser(userRepository.findByEmailIgnoreCase(email).get());
    }
}
