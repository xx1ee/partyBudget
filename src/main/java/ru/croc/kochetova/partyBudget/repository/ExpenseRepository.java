package ru.croc.kochetova.partyBudget.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.croc.kochetova.partyBudget.model.Expense;
import ru.croc.kochetova.partyBudget.model.User;

import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Integer> {
    List<Expense> findExpenseByUser(User user);
}
