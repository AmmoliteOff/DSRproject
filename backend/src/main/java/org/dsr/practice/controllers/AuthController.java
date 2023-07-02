package org.dsr.practice.controllers;


import org.dsr.practice.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    AuthService authService;

    public AuthController(@Autowired AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/auth/sendCode")
    private ResponseEntity sendCode(@RequestParam("email") String email){
        if(authService.sendCode(email)){
            return new ResponseEntity(true, HttpStatus.OK);
        }
        else{
            return new ResponseEntity(false, HttpStatus.NOT_FOUND);
        }
    }
//    @GetMapping(value = "/login-error")
//    private ResponseEntity GetLoginErrorController(){
//        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
//    }
}
