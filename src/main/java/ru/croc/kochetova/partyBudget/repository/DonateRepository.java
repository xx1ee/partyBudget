package ru.croc.kochetova.partyBudget.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.croc.kochetova.partyBudget.model.Income;

@Repository
public interface DonateRepository extends JpaRepository<Income, Integer> {
}
