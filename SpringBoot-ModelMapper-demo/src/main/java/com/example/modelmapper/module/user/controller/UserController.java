package com.example.modelmapper.module.user.controller;

import com.example.modelmapper.module.user.entity.User;
import com.example.modelmapper.module.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/gogo")
    public ResponseEntity<User> saveUser(@RequestBody User user){

        return ResponseEntity.ok(userService.save(user));
    }
}
