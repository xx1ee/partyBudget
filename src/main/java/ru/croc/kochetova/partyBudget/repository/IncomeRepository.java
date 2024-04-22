package ru.croc.kochetova.partyBudget.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.croc.kochetova.partyBudget.model.Income;
import ru.croc.kochetova.partyBudget.model.User;

import java.util.List;
@Repository
public interface IncomeRepository extends JpaRepository<Income, Integer> {
    List<Income> findIncomesByUser(User user);
}
