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
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class UserController {

    UsersService usersService;

    public UserController(@Autowired UsersService usersService) {
        this.usersService = usersService;
    }

    @JsonView(JsonViews.BasicDetails.class)
    @GetMapping(value = "/api/getUserInfo")
    public ResponseEntity getUserInfo(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = usersService.getUser(auth.getPrincipal().toString());
        return new ResponseEntity(user, HttpStatus.OK);
    }

    @PostMapping(value = "/api/settings")
    public ResponseEntity setUserSettings(@RequestBody Map<String, Object> requestBody){
        var name = requestBody.get("name").toString();
        var surname = requestBody.get("surname").toString();
        var imgLink = requestBody.get("imgLink").toString();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        usersService.setSettings(auth.getPrincipal().toString(), name, surname, imgLink);
        return new ResponseEntity(HttpStatus.OK);
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
