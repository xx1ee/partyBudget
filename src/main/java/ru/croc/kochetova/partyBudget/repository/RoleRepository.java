package ru.croc.kochetova.partyBudget.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.croc.kochetova.partyBudget.model.Role;
@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findFirstByAuthority(String authority);
}
