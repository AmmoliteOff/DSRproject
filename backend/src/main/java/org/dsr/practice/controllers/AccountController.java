package org.dsr.practice.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import org.dsr.practice.services.AccountsService;
import org.dsr.practice.utils.JsonViews;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
@CrossOrigin("http://localhost:3000")
public class AccountController {

    AccountsService accountsService;

    public AccountController(@Autowired AccountsService accountsService) {
        this.accountsService = accountsService;
    }

    @PostMapping("/api/createAccount")
    public ResponseEntity createAccount(@RequestParam(name = "title") String title, @RequestParam(name = "description") String description, @RequestParam(name = "users") String[] users){
            if(accountsService.createAccount(title, description, users))
                return new ResponseEntity(HttpStatus.OK);
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/api/account")
    @JsonView(JsonViews.BasicDetails.class)
    public ResponseEntity getAccount(@RequestParam(name = "accountId") Long accountId){
        var account = accountsService.getAccount(accountId);
        if(account!=null){
            return new ResponseEntity(account, HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }
}
