package com.doronin.mvc.repository;


import com.doronin.mvc.model.User;;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.jws.soap.SOAPBinding;
import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    public User findByUserId(Long id);

    public User findByLogin(String login);

    public User findByEmail(String email);

    public List<User> findAll();
}

