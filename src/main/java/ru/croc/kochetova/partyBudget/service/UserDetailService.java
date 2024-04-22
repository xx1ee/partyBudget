package ru.croc.kochetova.partyBudget.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.croc.kochetova.partyBudget.model.Role;
import ru.croc.kochetova.partyBudget.model.User;
import ru.croc.kochetova.partyBudget.repository.RoleRepository;
import ru.croc.kochetova.partyBudget.repository.UserRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

@Service
public class UserDetailService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    @Autowired
    public UserDetailService(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var userResult = userRepository.findByEmailIgnoreCase(username);
        return userResult.orElseThrow(() -> new UsernameNotFoundException(String.format("Пользователя с email \"%s\" не существует", username)));
    }
    public void save(User user) {
        user.setWallet_amount(generateAmount());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role role = roleRepository.findFirstByAuthority("ADMIN");
        if (userRepository.findFirstByAuthority(role).isPresent()) {
            role = roleRepository.findFirstByAuthority("USER");
        }
        user.setAuthority(role);
        userRepository.save(user);
    }
    public List<User> findAllByAuthority(Role role) {
        return userRepository.findAllByAuthority(role);
    }
    private BigDecimal generateAmount() {
        Random random = new Random();
        double randomNumber = random.nextDouble() * 4999999 + 1;
        return new BigDecimal(randomNumber).setScale(2, BigDecimal.ROUND_HALF_UP);
    }
}
