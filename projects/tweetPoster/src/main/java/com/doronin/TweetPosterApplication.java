package com.doronin;

import com.doronin.bootstrap.DbInitializer;
import com.doronin.mvc.model.Tweet;
import com.doronin.mvc.repository.TweetRepository;
import com.doronin.mvc.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;

import java.util.List;

@Configuration
@ComponentScan
@EnableAutoConfiguration
@EnableGlobalMethodSecurity(securedEnabled = true)
@SpringBootApplication
public class TweetPosterApplication extends SpringBootServletInitializer {

    @Autowired
    private UserRepository userRep;
    @Autowired
    private TweetRepository tweetRep;

    public static void main(String[] args) {
        SpringApplication.run(TweetPosterApplication.class, args);
    }

    @Bean
    public Java8TimeDialect java8TimeDialect() {
        return new Java8TimeDialect();
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(TweetPosterApplication.class);
    }

    @Bean
    CommandLineRunner runner() {
        return args -> {
            DbInitializer.initDb(userRep, tweetRep);
        };
    }
}
