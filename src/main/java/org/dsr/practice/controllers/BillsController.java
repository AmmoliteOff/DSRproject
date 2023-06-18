package org.dsr.practice.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import org.dsr.practice.dao.AccountsDAO;
import org.dsr.practice.dao.BillsDAO;
import org.dsr.practice.dao.UsersDAO;
import org.dsr.practice.models.Bill;
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
import java.util.HashMap;
import java.util.List;

@RestController
public class BillsController {

    @Autowired
    UsersDAO UsersDao;

    @Autowired
    BillsDAO BillsDao;

    @Autowired
    AccountsDAO accountsDao;

    @PostMapping(value = "/api/addBill")
    public ResponseEntity addBill(@RequestParam(value="users") String[] users, @RequestParam(value = "title")String title, @RequestParam(value = "description") String description, @RequestParam(value = "accountId") Long accountId){
        List<User> usersInBill = new ArrayList<User>();
        HashMap<User, Double> userDebt= new HashMap<User, Double>();
        for(int i = 0; i<users.length;i++){
            var userDeserialize = users[i].split(";");
            User user = UsersDao.getUserById(Long.parseLong(userDeserialize[0]));
            usersInBill.add(user);
            userDebt.put(user, Double.parseDouble(userDeserialize[1]));
        }

        var acc = accountsDao.getById(accountId);

        for(int i = 0; i<usersInBill.size(); i++){
            BillsDao.addBill(title, description, userDebt.get(usersInBill.get(i)), acc, usersInBill.get(i));
        }

        return new ResponseEntity(HttpStatus.OK);
    }
}
