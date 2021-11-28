package com.dp.controller;

import com.dp.model.User;
import com.dp.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/user")
@Slf4j
public class UserController {

    private UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        log.info("Fetching all users");
        return new ResponseEntity<>(userRepository.findAll(), OK);
    }

    @PostMapping
    public ResponseEntity<String> saveUser(@RequestBody User user) {
        log.info("saving user" + user);
        userRepository.save(user);
        return new ResponseEntity<>("User saved successfully ", OK);
    }
}
