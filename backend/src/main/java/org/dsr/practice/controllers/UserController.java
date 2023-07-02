package org.dsr.practice.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import org.dsr.practice.models.User;
import org.dsr.practice.services.UsersService;
import org.dsr.practice.utils.JsonViews;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    UsersService usersService;

    public UserController(@Autowired UsersService usersService) {
        this.usersService = usersService;
    }

    @JsonView(JsonViews.LimitedPublic.class)
    @GetMapping(value = "/api/getUserInfo")
    public ResponseEntity getUserInfo(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = usersService.getUser(auth.getPrincipal().toString());
        return new ResponseEntity(user, HttpStatus.OK);
    }

    @JsonView(JsonViews.BasicDetails.class)
    @GetMapping(value = "/api/fetchUser")
    public ResponseEntity fetchUser(@RequestParam(name = "userId") Long userId){
        var user = usersService.getUser(userId);
        if(user!=null)
            return new ResponseEntity(user, HttpStatus.OK);
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }
}
