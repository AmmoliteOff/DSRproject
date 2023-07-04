package org.dsr.practice.services;

import org.dsr.practice.models.User;
import org.dsr.practice.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsersService {
    private UserRepository userRepository;
    public UsersService(@Autowired UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User getUser(Long id){
        return userRepository.findByUserId(id);
    }

    public User getUser(String pr){
        return userRepository.findByEmail(pr.replaceAll(" ", ""));
    }

    public void setSettings(String pr, String name, String surname, String imgLink){
        User user = getUser(pr);
        user.setName(name);
        user.setSurname(surname);
        user.setImgLink(imgLink);
        Update(user);
    }

    public void Update(User user){
        userRepository.save(user);
    }
}
