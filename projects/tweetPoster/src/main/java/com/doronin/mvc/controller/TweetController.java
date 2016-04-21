package com.doronin.mvc.controller;

import com.doronin.mvc.model.Tweet;
import com.doronin.mvc.service.TweetService;
import com.doronin.mvc.service.UserService;
import com.doronin.mvc.validation.TweetFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;

@Controller
public class TweetController {

    @Autowired
    TweetFormValidator tweetFormValidator;
    private TweetService tweetService;
    @Autowired
    private UserService userService;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(tweetFormValidator);
    }

    @Autowired
    public void setTweetService(TweetService tweetService) {
        this.tweetService = tweetService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/summary", method = RequestMethod.POST)
    public ModelAndView shareTweet(@ModelAttribute("tweetForm") @Valid Tweet tweetForm, BindingResult bindingResult) {
        ModelAndView modView;
        if (bindingResult.hasErrors()) {
            modView = new ModelAndView("summary");
            modView.addObject("tweets", tweetService.getAllTweetsOrderedByDesc());
        } else {
            org.springframework.security.core.userdetails.User owner = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            tweetForm.setUser(userService.findByLogin(owner.getUsername()));
            tweetService.saveTweet(tweetForm);
            modView = new ModelAndView(new RedirectView("/summary"));
        }
        return modView;
    }


    @RequestMapping(value = "/summary", method = RequestMethod.GET)
    public ModelAndView summary() {
        ModelAndView model = new ModelAndView();
        model.addObject("tweetForm", new Tweet());
        model.addObject("tweets", tweetService.getAllTweetsOrderedByDesc());
        model.setViewName("summary");
        return model;
    }
}
