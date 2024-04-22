package ru.croc.kochetova.partyBudget.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.croc.kochetova.partyBudget.model.Income;
import ru.croc.kochetova.partyBudget.model.MembershipApplication;
import ru.croc.kochetova.partyBudget.model.Status;
import ru.croc.kochetova.partyBudget.model.User;

import java.util.Collection;
import java.util.List;

@Repository
public interface MembershipApplicationRepository extends JpaRepository<MembershipApplication, Integer> {
    boolean existsByUserAndStatus(User user, Status status);
    @Query("SELECT m FROM MembershipApplication m join fetch m.user WHERE m.status IN :status")
    List<MembershipApplication> findAllByStatusIn(Collection<Status> status);
}
