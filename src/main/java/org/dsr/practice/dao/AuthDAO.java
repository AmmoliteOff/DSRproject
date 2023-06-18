package org.dsr.practice.dao;

import org.aspectj.apache.bcel.classfile.Code;
import org.dsr.practice.models.User;
import org.dsr.practice.repos.UserRepository;
import org.dsr.practice.utils.generators.CodeGenerator;
import org.dsr.practice.utils.generators.Sha256Generator;
import org.dsr.practice.utils.mail.MailSenderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class AuthDAO {
    @Autowired
    UserRepository repo;

    @Autowired
    MailSenderImpl mailSender;

    public boolean sendCode(String email){
        User user;
        try {
            user = repo.findByEmail(email);
            user.setCode(CodeGenerator.getNewCode());
            repo.save(user);
        }
        catch(Exception e){
            user = new User(email, "New", "User");
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
                if(usr.getCode().equals(password)){
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
