package ru.croc.kochetova.partyBudget.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.croc.kochetova.partyBudget.model.MembershipApplication;
import ru.croc.kochetova.partyBudget.model.ReceivingMoneyApplication;
import ru.croc.kochetova.partyBudget.model.Status;

import java.util.Collection;
import java.util.List;

public interface ReceivingMoneyApplicationRepository extends JpaRepository<ReceivingMoneyApplication, Integer> {
    @Query("SELECT r FROM ReceivingMoneyApplication r join fetch r.user WHERE r.status IN :status")
    List<ReceivingMoneyApplication> findAllByStatusIn(Collection<Status> status);
}
