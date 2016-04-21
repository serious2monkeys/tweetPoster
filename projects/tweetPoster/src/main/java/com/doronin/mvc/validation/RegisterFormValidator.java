package com.doronin.mvc.validation;

import com.doronin.mvc.model.User;
import com.doronin.mvc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class RegisterFormValidator implements Validator {

    @Autowired
    @Qualifier("emailValidator")
    EmailValidator emailValidator;

    @Autowired
    UserService userService;

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        User user = (User) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "login", "NotEmpty.registerForm.login");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty.registerForm.email");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty.registerForm.password");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "NotEmpty.registerForm.firstName");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "NotEmpty.registerForm.lastName");

        if (!user.getEmail().isEmpty() && !emailValidator.valid(user.getEmail())) {
            errors.rejectValue("email", "Pattern.registerForm.email");
        }

        if (userService.findByEmail(user.getEmail()) != null) {
            errors.rejectValue("email", "Unique.registerForm.email");
        }

        if (userService.findByLogin(user.getLogin()) != null) {
            errors.rejectValue("login", "Unique.registerForm.login");
        }
    }
}
