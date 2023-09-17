package com.project.smartICT.controllers;

import com.project.smartICT.entities.User;
import com.project.smartICT.services.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @PostMapping
    public User createOneUser(@RequestBody User newUser){
        return userService.createOneUser(newUser);
    }


    @GetMapping("/{userId}")
    public User getOneUser(@PathVariable Long userId){
        return userService.getOneUser(userId);
    }


    @PutMapping("/{userId}")
    public User updateOneUser(@PathVariable Long userId, @RequestBody User updateUser){
        return userService.updateOneUser(userId, updateUser);
    }

    @DeleteMapping("/{userId}")
    public void deleteOneUser(@PathVariable Long userId){
        userService.deleteOneUser(userId);
    }
}
