package org.dsr.practice.dao;

import org.dsr.practice.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.dsr.practice.repos.UserRepository;

@Component
public class UsersDAO {
    @Autowired
    UserRepository repo;


    public User getUser(String email) {
        return repo.findByEmail(email);
    }

    public User getUser(long id){
        return repo.findById(id).get();
    }
}
