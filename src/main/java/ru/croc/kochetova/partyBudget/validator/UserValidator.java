package ru.croc.kochetova.partyBudget.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.croc.kochetova.partyBudget.model.User;
import ru.croc.kochetova.partyBudget.repository.UserRepository;

@Component
public class UserValidator  implements Validator {
    UserRepository userRepository;
    @Autowired
    public UserValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        if (userRepository.findByEmailIgnoreCase(user.getEmail()).isPresent()) {
            errors.rejectValue("email", "", "Такой email уже существует");
        }
    }
}