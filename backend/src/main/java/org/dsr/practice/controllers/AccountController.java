package org.dsr.practice.controllers;

import org.dsr.practice.dao.AccountsDAO;
import org.dsr.practice.dao.UsersDAO;
import org.dsr.practice.models.Account;
import org.dsr.practice.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

@RestController
public class AccountController {
    UsersDAO usersDAO;

    AccountsDAO accountsDAO;


    public AccountController(@Autowired UsersDAO usersDAO, @Autowired AccountsDAO accountsDAO) {
        this.usersDAO = usersDAO;
        this.accountsDAO = accountsDAO;
    }

    @PostMapping("/api/createAccount")
    public ResponseEntity createAccount(@RequestParam(name = "title") String title, @RequestParam(name = "description") String description, @RequestParam(name = "users") String[] users){
        try {
            Set<User> usersIn = new HashSet<User>();
            for (String str:users) {
                var usr = usersDAO.getUserById(Long.parseLong(str));
                usersIn.add(usr);
            }
            Account acc= new Account(title, description, usersIn);
            var account = accountsDAO.Add(acc);
            for(User usr:usersIn){
                var c = usr.getAccounts();
                c.add(account);
                usr.setAccounts(c);
                usersDAO.update(usr);
            }
            return new ResponseEntity(HttpStatus.OK);
        }
        catch (Exception e){
            //throw
            var a = 0;
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }
}
