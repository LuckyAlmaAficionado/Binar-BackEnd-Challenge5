package com.binar.challenge5.challenge5.controller;

import com.binar.challenge5.challenge5.model.User;
import com.binar.challenge5.challenge5.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RestController
@RequestMapping(path = "api/v1/user")
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping
    public List<User> getUsers() {
        return service.getUsers();
    }

    @PostMapping
    public String postUser(@RequestParam String username,
                           @RequestParam String email,
                           @RequestParam String password) {
        return service.addUser(username, email, password);
    }

    @PutMapping(path = "{userId}")
    public String updateUser(
            @PathVariable("userId") Long userId,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String password
    ) {
        return service.updateUser(userId, username, email, password);
    }

    @DeleteMapping(path = "{userId}")
    public String deleteUser(@PathVariable Long userId) {
        return service.deleteUser(userId);
    }

}
