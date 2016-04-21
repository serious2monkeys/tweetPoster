package com.doronin.mvc.service.security;

import com.doronin.mvc.model.User;
import com.doronin.mvc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class LoginDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User someUser = userService.findByLogin(username);
        if (someUser == null) {
            throw new UsernameNotFoundException("Такого пользователя нет");
        }
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("USER"));

        org.springframework.security.core.userdetails.User accessedUser =
                new org.springframework.security.core.userdetails.User(
                        someUser.getLogin(), someUser.getPassword(), true, true, true,
                        true, authorities);
        return accessedUser;
    }
}
