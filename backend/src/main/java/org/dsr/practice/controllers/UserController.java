package org.dsr.practice.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import org.dsr.practice.dao.UsersDAO;
import org.dsr.practice.models.User;
import org.dsr.practice.utils.JsonViews;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    UsersDAO usersDAO;

    public UserController(@Autowired UsersDAO usersDAO) {
        this.usersDAO = usersDAO;
    }

    @JsonView(JsonViews.LimitedPublic.class)
    @GetMapping(value = "/api/getUserInfo")
    public ResponseEntity getUserInfo(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = usersDAO.getUser(auth.getPrincipal().toString());
        return new ResponseEntity(user, HttpStatus.OK);
    }
}
