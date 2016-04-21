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
        Tweet sampleTweet = new Tweet("Сначала было слово");
        sampleTweet.setUser(admin);
        ur.save(admin);
        tr.save(sampleTweet);
        Tweet second = new Tweet("А за ним ещё одно");
        second.setUser(admin);
        tr.save(second);

        User sammy = new User();
        sammy.setLogin("sammy");
        sammy.setFirstName("Samantha");
        sammy.setLastName("Withweakie");
        sammy.setEmail("sammy@mymail.com");
        sammy.setPassword(SecurityConfiguration.encoder().encode("MyPass"));
        Tweet girlTweet = new Tweet("Я тоже могу тут писать");
        girlTweet.setUser(sammy);
        ur.save(sammy);
        tr.save(girlTweet);
    }
}
