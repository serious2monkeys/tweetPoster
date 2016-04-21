package com.doronin.mvc.service;

import com.doronin.mvc.model.Tweet;
import com.doronin.mvc.repository.TweetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaContext;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TweetService {

    @Autowired
    private TweetRepository tweetRepository;

    @Autowired
    private JpaContext jpaContext;

    public List<Tweet> getAllTweets() {
        return tweetRepository.findAll();
    }

    public List<Tweet> getAllTweetsOrderedByDesc() {
        EntityManager manager = jpaContext.getEntityManagerByManagedType(Tweet.class);
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Tweet> query = builder.createQuery(Tweet.class);
        Root<Tweet> tweetRoot = query.from(Tweet.class);
        query.select(tweetRoot);
        query.orderBy(builder.desc(tweetRoot.get("dateCreated")));
        return manager.createQuery(query).getResultList();
    }

    public void saveTweet(Tweet toSave) {
        toSave.setDateCreated(LocalDateTime.now());
        tweetRepository.save(toSave);
    }
}
