package com.doronin.mvc.service;

import com.doronin.mvc.config.SecurityConfiguration;
import com.doronin.mvc.model.User;
import com.doronin.mvc.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRep;

    public User findByLogin(String login) {
        return userRep.findByLogin(login);
    }

    public User findById(long id) {
        return userRep.findByUserId(id);
    }

    public void saveUser(User toSave) {
        toSave.setPassword(SecurityConfiguration.encoder().encode(toSave.getPassword()));
        userRep.save(toSave);
    }

    public List<User> getAllUsers() {
        return userRep.findAll();
    }

    public User findByEmail(String email) {
        return userRep.findByEmail(email);
    }
}
