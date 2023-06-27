package org.dsr.practice.controllers;

import org.dsr.practice.dao.AuthDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@CrossOrigin(origins = "https://localhost:3000")
public class AuthController {

    AuthDAO authDAO;

    public AuthController(@Autowired AuthDAO authDAO) {
        this.authDAO = authDAO;
    }

    @PostMapping("/auth/sendCode")
    //@CrossOrigin
    private ResponseEntity sendCode(@RequestParam("email") String email){
        if(authDAO.sendCode(email)){
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
//
//    @GetMapping(value = "/login")
//    private String GetLoginController(){
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if(auth.getPrincipal()!="anonymousUser"){
//            return "redirect:main";
//        }
//        else {
//            return "login";
//        }
//    }
}
