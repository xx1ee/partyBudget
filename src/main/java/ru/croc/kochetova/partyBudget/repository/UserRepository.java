package ru.croc.kochetova.partyBudget.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.croc.kochetova.partyBudget.model.Role;
import ru.croc.kochetova.partyBudget.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("SELECT u FROM User u join fetch u.authority where u.email = ?1")
    Optional<User> findByEmailIgnoreCase(String email);
    List<User> findAllByAuthority(Role role);
    Optional<User> findFirstByAuthority(Role role);
}
