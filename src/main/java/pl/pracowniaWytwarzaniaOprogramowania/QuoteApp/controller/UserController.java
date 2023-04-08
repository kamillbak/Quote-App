package pl.pracowniaWytwarzaniaOprogramowania.QuoteApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.pracowniaWytwarzaniaOprogramowania.QuoteApp.model.LoginRequest;
import pl.pracowniaWytwarzaniaOprogramowania.QuoteApp.model.User;
import pl.pracowniaWytwarzaniaOprogramowania.QuoteApp.repository.UserRepository;

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
    public int login(@RequestBody LoginRequest loginRequest) {
        User userFromDB = userRepository.getUserByUsername(loginRequest.getUsername());
        if (userFromDB == null || !loginRequest.getPassword().equals(userFromDB.getPassword()) ) {
            return -1;
        }
        else {
            // token i user ID
            return userFromDB.getId();
        }
    }

}
