package com.doronin.mvc.repository;

import com.doronin.mvc.model.Tweet;
import com.doronin.mvc.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TweetRepository extends CrudRepository<Tweet, Long> {
    public List<Tweet> findByUser(User user);

    //public List<Tweet> findByUserByOrderByDateCreatedDesc(User user);

    public List<Tweet> findAll();

    //public List<Tweet> findAllByOrderByDateCreatedDesc();


}
