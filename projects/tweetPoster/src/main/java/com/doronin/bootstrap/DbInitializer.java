package com.doronin.bootstrap;

import com.doronin.mvc.config.SecurityConfiguration;
import com.doronin.mvc.model.Tweet;
import com.doronin.mvc.model.User;
import com.doronin.mvc.repository.TweetRepository;
import com.doronin.mvc.repository.UserRepository;

public class DbInitializer {

    public static void initDb(UserRepository ur, TweetRepository tr) {
        User admin = new User();
        admin.setLogin("admin");
        admin.setFirstName("John");
        admin.setLastName("Smith");
        admin.setPassword(SecurityConfiguration.encoder().encode("admin"));
        admin.setEmail("admin@mymail.com");
        Tweet sampleTweet = new Tweet("Sample Tweet! Youpiiieee!");
        sampleTweet.setUser(admin);
        ur.save(admin);
        tr.save(sampleTweet);
        Tweet second = new Tweet("Just for test");
        second.setUser(admin);
        tr.save(second);

        User sammy = new User();
        sammy.setLogin("sammy");
        sammy.setFirstName("Samantha");
        sammy.setLastName("Withweakie");
        sammy.setEmail("sammy@mymail.com");
        sammy.setPassword(SecurityConfiguration.encoder().encode("MyPass"));
        Tweet girlTweet = new Tweet("I'm just a girl");
        girlTweet.setUser(sammy);
        ur.save(sammy);
        tr.save(girlTweet);
    }
}
