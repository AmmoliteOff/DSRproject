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
        var a = repo.findByEmail(email);
        return repo.findByEmail(email.replaceAll("\n","").replaceAll(" ", ""));
    }

    public void update(User user){
        repo.save(user);
    }
    public User getUserById(Long id){
        return repo.findByUserId(id);
    }

    public User getUser(long id){
        return repo.findById(id).get();
    }
}
