package com.doronin.mvc.controller;

import com.doronin.mvc.model.User;
import com.doronin.mvc.service.UserService;
import com.doronin.mvc.validation.RegisterFormValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;

@Controller
public class UserController {
    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    RegisterFormValidator registerFormValidator;
    private UserService userService;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(registerFormValidator);
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView redirectToLogIn() {
        return new ModelAndView(new RedirectView("/login"));
    }

    @RequestMapping(value = "/show/{id}", method = RequestMethod.GET)
    public ModelAndView showUserInfo(@PathVariable("id") long id) {
        ModelAndView model = new ModelAndView();
        User current = userService.findById(id);
        model.addObject("user", current);
        model.setViewName("show");
        return model;
    }

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public ModelAndView showRegisterForm() {
        ModelAndView model = new ModelAndView("signUpForm");
        model.addObject("registerForm", new User());
        return model;
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public ModelAndView registerUser(@ModelAttribute("registerForm") @Valid User registerForm, BindingResult bindingResult) {
        ModelAndView modView;
        if (bindingResult.hasErrors()) {
            modView = new ModelAndView("signUpForm");
        } else {
            userService.saveUser(registerForm);
            modView = new ModelAndView(new RedirectView("/login?registered"));
        }
        return modView;
    }
}
