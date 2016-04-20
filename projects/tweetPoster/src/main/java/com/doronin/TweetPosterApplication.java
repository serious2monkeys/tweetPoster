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
//@EnableJpaRepositories(basePackages = {"com.doronin.mvc.repository"})
//@EntityScan(basePackages = {"com.doronin.mvc.model"})
public class TweetPosterApplication extends SpringBootServletInitializer {

	@Autowired
	private UserRepository userRep;
	@Autowired
	private TweetRepository tweetRep;

	@Bean
	public Java8TimeDialect java8TimeDialect() {
		return new Java8TimeDialect();
	}

	public static void main(String[] args) {
		SpringApplication.run(TweetPosterApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(TweetPosterApplication.class);
	}

	@Bean
	CommandLineRunner runner() {
		return args -> {
			DbInitializer.initDb(userRep, tweetRep);
			userRep.findAll().forEach(it -> {
				System.out.println(it.getFirstName() + " " + it.getLastName() + " " + it.getEmail() + " Num of Tweets: " + it.getTweets().size());
					System.out.println ("PROS:");
					it.getTweets().forEach(some -> System.out.println(some.getTweetId() + " : " + some.getUser().getFirstName() + " : " + some.getContent()));
					List<Tweet> tweets =  tweetRep.findByUser(it);
					System.out.println("I've found: " + tweets.size() + "\n");
					tweets.forEach(tweet -> System.out.println(tweet.getContent()));
				}
			);
			System.out.println("ALL tweets: ");
			tweetRep.findAll().forEach(tweet -> System.out.println(tweet.getTweetId() + " " + tweet.getUser().getFirstName() + " " + tweet.getContent()));
		};
	}
}
