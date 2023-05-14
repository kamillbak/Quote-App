package pl.pracowniaWytwarzaniaOprogramowania.QuoteApp.controller;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.pracowniaWytwarzaniaOprogramowania.QuoteApp.model.LoginRequest;
import pl.pracowniaWytwarzaniaOprogramowania.QuoteApp.model.User;
import pl.pracowniaWytwarzaniaOprogramowania.QuoteApp.repository.UserRepository;
import pl.pracowniaWytwarzaniaOprogramowania.QuoteApp.security.LoggedUser;

import java.util.Date;



@RestController
@RequestMapping("/user")
public class UserController {

    LoggedUser loggedUser = LoggedUser.getLoggedUser(); // singleton object

    @Autowired
    UserRepository userRepository;

    @GetMapping("/test")
    public String test() {
        return "hello test";
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        String newUserName = user.getUsername();
        if(userRepository.checIfUsernameExist(newUserName).intValue() >0) {
            return new ResponseEntity("Username already exists", HttpStatusCode.valueOf(400));
        }
        else {
            userRepository.registerUser(user);
            return new ResponseEntity("User registered successfully", HttpStatusCode.valueOf(200));
        }
    }

    @PostMapping(value = "/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        User userFromDB = userRepository.getUserByUsername(loginRequest.getUsername());
        if (userFromDB == null) {
            return new ResponseEntity("Username does not exist", HttpStatusCode.valueOf(401));
        }
        else if (!loginRequest.getPassword().equals(userFromDB.getPassword())) {
            return new ResponseEntity("Wrong password", HttpStatusCode.valueOf(401));
        }
        else {
            // set user logged
            loggedUser.setId(userFromDB.getId());
            loggedUser.setUsername(userFromDB.getUsername());
            loggedUser.setPassword(userFromDB.getPassword());
            //create token and return
            long currentTimeMillis =System.currentTimeMillis();
            String token = Jwts.builder()
                    .setSubject(userFromDB.getUsername())
                    .claim("userLoggedId", loggedUser.getId())
                    .setIssuedAt(new Date(currentTimeMillis))
                    .setExpiration(new Date(currentTimeMillis + 30*60*1000)) // Valid for 1 minute
                    .signWith(SignatureAlgorithm.HS512, userFromDB.getPassword())
                    .compact();

            return new ResponseEntity(token, HttpStatusCode.valueOf(200));
        }
    }

}
