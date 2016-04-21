package com.doronin.mvc.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;


@Entity
@Table(name = "tweets")
public class Tweet implements Serializable {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long tweetId;

    @Column(name = "content", length = 1024, nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @DateTimeFormat(pattern = "dd-MMM-YYYY")
    @Column(name = "dateCreated", nullable = false)
    private LocalDateTime dateCreated;

    public Tweet() {
        dateCreated = LocalDateTime.now();
    }

    public Tweet(String text) {
        content = text;
        dateCreated = LocalDateTime.now();
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime time) {
        dateCreated = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String text) {
        content = text;
    }

    public Long getTweetId() {
        return tweetId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User owner) {
        user = owner;
    }
}
