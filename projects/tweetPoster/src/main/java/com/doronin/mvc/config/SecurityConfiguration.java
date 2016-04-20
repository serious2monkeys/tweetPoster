package com.doronin.mvc.config;

import com.doronin.mvc.security.UserAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserAuthenticationProvider authenticationProvider;

    @Bean
    public static PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/css/**")
                .antMatchers("/js/**")
                .antMatchers("/fonts/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //http.authorizeRequests().antMatchers("/**").permitAll();
        http.authorizeRequests().antMatchers("/signup", "/register").permitAll();
        http.authorizeRequests().anyRequest().fullyAuthenticated();
        http.formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/summary")
                .failureUrl("/login?error")
                .permitAll()
                .and()
                .logout().
                logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID");

                http.sessionManagement()
                        .sessionFixation()
                        .newSession();
                /*
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .clearAuthentication(true)
                .deleteCookies("spring.thymeleaf.cache")
                .permitAll();

            */

        /*
        http
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/summary")
                .permitAll()
                .and()
                .logout()
                .permitAll();
                */
    }
}
