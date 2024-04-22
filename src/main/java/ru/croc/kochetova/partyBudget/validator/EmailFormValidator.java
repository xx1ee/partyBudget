package ru.croc.kochetova.partyBudget.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.croc.kochetova.partyBudget.dto.EmailForm;
import ru.croc.kochetova.partyBudget.repository.UserRepository;
@Component
public class EmailFormValidator implements Validator {
    UserRepository userRepository;
    @Autowired
    public EmailFormValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return EmailForm.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        EmailForm emailForm = (EmailForm) target;
        if (userRepository.findByEmailIgnoreCase(emailForm.getEmail()).isEmpty()) {
            errors.rejectValue("email", "", "Такого пользователя не существует");
        }
    }
}
