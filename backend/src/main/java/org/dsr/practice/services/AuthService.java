package org.dsr.practice.services;

import org.dsr.practice.models.User;
import org.dsr.practice.repos.UserRepository;
import org.dsr.practice.utils.generators.CodeGenerator;
import org.dsr.practice.utils.mail.MailSenderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    UserRepository repo;

    MailSenderImpl mailSender;

    public AuthService(@Autowired UserRepository repo, @Autowired MailSenderImpl mailSender) {
        this.repo = repo;
        this.mailSender = mailSender;
    }

    public boolean sendCode(String email){
        User user;
        try {
            user = repo.findByEmail(email);
            user.setCode(CodeGenerator.getNewCode());
            repo.save(user);
        }
        catch(Exception e){
            user = new User(email);
            repo.save(user);
        }
        try {
            mailSender.sendSimpleMessage(user.getEmail(), "Code", user.getCode());
            return true;
        }
        catch (Exception e){
            return false;
        }
    }
    public String GetRole(String email, String password) throws AuthenticationException {
        try {
            var usr = repo.findByEmail(email);
            if(usr.getCode().equals(password)) {
                return usr.getRole();
            }
            return null;
        }
        catch (AuthenticationException e){
            return null;
        }
    }
    public void changeCode(String email){
        try{
            User user = repo.findByEmail(email);
            user.setCode(CodeGenerator.getNewCode());
            repo.save(user);
        }
        catch (Exception e){

        }
    }
}
