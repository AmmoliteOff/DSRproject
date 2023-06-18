package org.dsr.practice.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import org.dsr.practice.dao.AccountsDAO;
import org.dsr.practice.dao.UsersDAO;
import org.dsr.practice.models.User;
import org.dsr.practice.utils.JsonViews;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class AccountsController {
    @Autowired
    UsersDAO usersDAO;

    @Autowired
    AccountsDAO accountsDAO;


    @JsonView(JsonViews.LimitedPublic.class)
    @GetMapping(value = "/api/getUserAccounts")
    public ResponseEntity getUserBills(){
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User user = usersDAO.getUser(auth.getPrincipal().toString());
            return new ResponseEntity(user.getAccounts(), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity(e.toString(), HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping(value = "/api/createAccount")
    public ResponseEntity createAccount(@RequestParam(value="users") String[] users, @RequestParam(value = "title")String title, @RequestParam(value = "description") String description){
        try {
            List<User> usersToAdd = new ArrayList<User>();
            for(int i = 0; i<users.length; i++){
                usersToAdd.add(usersDAO.getUserById(Long.parseLong(users[i])));
            }
            var acc = accountsDAO.add(title, description, usersToAdd);
            for(int i = 0; i<usersToAdd.size(); i++){
                var usr = usersToAdd.get(i).getAccounts().add(acc);
                usersDAO.update(usersToAdd.get(i));
            }
            return new ResponseEntity(HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity(e.toString(), HttpStatus.UNAUTHORIZED);
        }
    }
}
