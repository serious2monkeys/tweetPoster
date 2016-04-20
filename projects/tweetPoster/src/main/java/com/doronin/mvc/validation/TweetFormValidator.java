package com.doronin.mvc.validation;

import com.doronin.mvc.model.Tweet;
import com.doronin.mvc.model.User;
import com.doronin.mvc.service.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class TweetFormValidator implements Validator {
    @Autowired
    TweetService tweetService;

    @Override
    public boolean supports(Class<?> clazz) {
        return Tweet.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "content", "NotEmpty.tweetForm.content");
    }
}
