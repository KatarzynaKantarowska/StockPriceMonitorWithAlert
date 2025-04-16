package com.kodilla.stockpricemonitorwithalert.controller;

import com.kodilla.stockpricemonitorwithalert.entity.UserEty;
import com.kodilla.stockpricemonitorwithalert.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public UserEty findById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @GetMapping("/all")
    public List<UserEty> findAll() {
        return userService.findAll();
    }

    @PostMapping
    public UserEty shouldCreateNewUser(@RequestBody UserEty userEty) {
        return userService.createUser(userEty);
    }

    @PutMapping("/{id}")
    public UserEty shouldUpdateUser(@PathVariable Long id, @RequestBody UserEty userEty) {
        return userService.updateUser(id, userEty);
    }

    @DeleteMapping("/{id}")
    public void shouldDeleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @GetMapping("/count")
    public long countUsers() {
        return userService.countUsers();
    }

    @GetMapping("/search")
    public UserEty findByEmail(@RequestParam String email) {
        return userService.findByEmail(email);
    }

    @GetMapping("/{id}/exists")
    public boolean doesUserExist(@PathVariable Long id) {
        return userService.userExists(id);
    }

    @PutMapping("/{id}/email")
    public ResponseEntity<Void> changeEmail(@PathVariable Long id, @RequestParam String email) {
        userService.changeEmail(id, email);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/reset")
    public ResponseEntity<Void> resetProfile(@PathVariable Long id) {
        userService.resetProfile(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/bulk")
    public ResponseEntity<Void> createUsers(@RequestBody List<UserEty> users) {
        userService.createManyUsers(users);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
