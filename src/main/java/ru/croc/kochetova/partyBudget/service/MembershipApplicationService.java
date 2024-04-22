package ru.croc.kochetova.partyBudget.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.croc.kochetova.partyBudget.exception.ApplicationCreatedException;
import ru.croc.kochetova.partyBudget.exception.UserNotFoundException;
import ru.croc.kochetova.partyBudget.model.MembershipApplication;
import ru.croc.kochetova.partyBudget.model.Status;
import ru.croc.kochetova.partyBudget.model.User;
import ru.croc.kochetova.partyBudget.repository.MembershipApplicationRepository;
import ru.croc.kochetova.partyBudget.repository.RoleRepository;
import ru.croc.kochetova.partyBudget.repository.UserRepository;

import java.util.List;

@Service
public class MembershipApplicationService {
    MembershipApplicationRepository membershipApplicationRepository;
    UserRepository userRepository;
    RoleRepository roleRepository;
    private static final List<Status> stats = List.of(Status.CREATED, Status.CONSIDERED);
    @Autowired
    public MembershipApplicationService(MembershipApplicationRepository membershipApplicationRepository, UserRepository userRepository, RoleRepository roleRepository) {
        this.membershipApplicationRepository = membershipApplicationRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }
    public void saveApplication(String user) {
        User userFind = userRepository.findByEmailIgnoreCase(user).orElseThrow(UserNotFoundException::new);
        if (membershipApplicationRepository.existsByUserAndStatus(userFind, Status.CREATED) ||
                membershipApplicationRepository.existsByUserAndStatus(userFind, Status.CONSIDERED)) {
            throw new ApplicationCreatedException();
        } else {
            MembershipApplication membershipApplication = new MembershipApplication(userFind);
            membershipApplicationRepository.save(membershipApplication);
        }
    }
    public List<MembershipApplication> findAllUnfinishedApplications() {
        return membershipApplicationRepository.findAllByStatusIn(stats);
    }
    @Transactional
    public void updateStatus(Integer id, Status status) {
        MembershipApplication request = membershipApplicationRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid request ID"));
        request.setStatus(status);
        membershipApplicationRepository.save(request);
        if (status.equals(Status.COMPLETED)) {
            request.getUser().setAuthority(roleRepository.findFirstByAuthority("PARTY_MEMBER"));
            userRepository.save(request.getUser());
        }
    }
    public void deleteMember(Integer id) {
        User user = userRepository.findById(id).get();
        user.setAuthority(roleRepository.findFirstByAuthority("USER"));
        userRepository.save(user);
    }
}
