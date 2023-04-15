package pl.pracowniaWytwarzaniaOprogramowania.QuoteApp.controller;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.pracowniaWytwarzaniaOprogramowania.QuoteApp.model.LoginRequest;
import pl.pracowniaWytwarzaniaOprogramowania.QuoteApp.model.User;
import pl.pracowniaWytwarzaniaOprogramowania.QuoteApp.repository.UserRepository;

import java.util.Date;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @PostMapping("/register")
    public int register(@RequestBody User user) {
        String newUserName = user.getUsername();
        if(userRepository.checIfUsernameExist(newUserName).intValue() >0) {
            return -1;
        }
        else {
            userRepository.registerUser(user);
            return 1;
        }
    }

    @PostMapping(value = "/login")
    public String login(@RequestBody LoginRequest loginRequest) {
        User userFromDB = userRepository.getUserByUsername(loginRequest.getUsername());
        if (userFromDB == null || !loginRequest.getPassword().equals(userFromDB.getPassword()) ) {
            return "Username does not exist";
        }
        else if (!loginRequest.getPassword().equals(userFromDB.getPassword())) {
            return "Password is not correct";
        }
        else {
            //create token and return
            long currentTimeMillis =System.currentTimeMillis();
            return Jwts.builder()
                    .setSubject(userFromDB.getUsername())
                    .claim("role", "user")
                    .setIssuedAt(new Date(currentTimeMillis))
                    .setExpiration(new Date(currentTimeMillis + 20*1000)) // Valid for 1 minute
                    .signWith(SignatureAlgorithm.HS512, userFromDB.getPassword())
                    .compact();
        }
    }

}
