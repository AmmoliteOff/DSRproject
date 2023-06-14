package org.dsr.practice.controllers;

import org.dsr.practice.dao.DebtsDAO;
import org.dsr.practice.dao.UsersDAO;
import org.dsr.practice.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DebtsController {
    @Autowired
    UsersDAO UsersDao;
    @Autowired
    DebtsDAO debtsDAO;

    @GetMapping(value = "/api/getDebts")
    private ResponseEntity getDebts(){
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User user = UsersDao.getUser(auth.getPrincipal().toString());
            var a = user.getDebts();
            var b = new ResponseEntity(user.getDebts(), HttpStatus.OK);
            return new ResponseEntity(user.getDebts(), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity(e.toString(), HttpStatus.BAD_REQUEST);
        }
    }
}
