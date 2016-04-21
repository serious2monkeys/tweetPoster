package com.doronin.mvc.model;

import com.doronin.mvc.config.SecurityConfiguration;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User implements Serializable {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Long userId;
    @Column(name = "login", nullable = false, unique = true)
    private String login;

    @Column(name = "firstname", nullable = false)
    private String firstName;
    @Column(name = "lastname", nullable = false)
    private String lastName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Tweet> tweets = new ArrayList<>();

    public User() {
    }

    public User(User parent) {
        login = parent.login;
        firstName = parent.firstName;
        lastName = parent.lastName;
        email = parent.lastName;
        password = SecurityConfiguration.encoder().encode(parent.password);
    }

    public Long getUserId() {
        return userId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String userLogin) {
        login = userLogin;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String fName) {
        firstName = fName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lName) {
        lastName = lName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String mail) {
        email = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String pass) {
        password = pass;
    }

    public List<Tweet> getTweets() {
        return tweets;
    }

    public void setTweets(List<Tweet> tweetList) {
        tweets = tweetList;
    }
}

