package ru.croc.kochetova.partyBudget.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.croc.kochetova.partyBudget.model.Role;
import ru.croc.kochetova.partyBudget.repository.RoleRepository;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }
    public Role findFirstByAuthority(String authority) {
        return roleRepository.findFirstByAuthority(authority);
    }
}
