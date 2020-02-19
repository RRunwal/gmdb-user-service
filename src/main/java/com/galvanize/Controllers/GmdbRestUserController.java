package com.galvanize.Controllers;

import com.galvanize.Models.*;
import com.galvanize.Services.GmdbUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
//@RequestMapping("/gmdb/restapi")
public class GmdbRestUserController {
    private final GmdbUserService gmdbUserService;

    public GmdbRestUserController(GmdbUserService gmdbUserService) {
        this.gmdbUserService = gmdbUserService;
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(HttpSession httpSession, @RequestBody Login login){
        User user = gmdbUserService.validateUser(login.getEmail(), login.getPassword());
        if (user == null){
            return ResponseEntity.badRequest().header("errorMessage","Invalid Credentials").build();
//            throw new RuntimeException("Invalid Credentials");
        }else{
            httpSession.setAttribute("screenname", user.getScreenName());
            httpSession.setAttribute("userid", user.getId());
            return ResponseEntity.ok(user);
        }

    }

    @PostMapping("/logout")
    public ResponseEntity logout(HttpSession httpSession, @RequestBody Logout logout){
        User user = gmdbUserService.validateUser(logout.getEmail(), logout.getPassword());
        if (user == null){
            return ResponseEntity.badRequest().build();
        }
        else {
            return ResponseEntity.ok().build();
        }
    }

    @PostMapping("/register")
    public ResponseEntity<User> createAccount(HttpSession httpSession, @RequestBody User reg) {
        User user = new User();
        user.setEmail(reg.getEmail());
        user.setPassword(reg.getPassword());
        user.setRepeatPassword(reg.getRepeatPassword());
        user.setScreenName(reg.getScreenName());

        String errMsg = "";

        boolean created = false;

        User userExisting = gmdbUserService.validateUser(reg.getEmail(), reg.getPassword());
        if (userExisting == null) {
            if (reg.getPassword().equals(reg.getRepeatPassword())) {
                created = gmdbUserService.createUser(user);
            } else {
                errMsg = "passwords don't match";
            }
        } else {
            errMsg = "user already exists";
        }

        if (created){
            return ResponseEntity.ok(user);
        }else{
            return ResponseEntity.badRequest().header("errorMessage","Could not create user: "+errMsg).build();
//            throw new RuntimeException("Could not create user: "+errMsg);
        }

    }

}

