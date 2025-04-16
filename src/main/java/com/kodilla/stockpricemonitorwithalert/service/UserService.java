package com.kodilla.stockpricemonitorwithalert.service;

import com.kodilla.stockpricemonitorwithalert.entity.UserEty;
import com.kodilla.stockpricemonitorwithalert.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEty findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found"));
    }

    public List<UserEty> findAll() {
        return userRepository.findAll();
    }

    public UserEty createUser(UserEty userEty) {
        if (userEty.getId() == null) {
            return userRepository.save(userEty);
        } else {
            throw new IllegalArgumentException("User ID must be null for a new user");
        }
    }

    public UserEty updateUser(Long userId, UserEty userEty) {
        if (userEty.getId() != null && userEty.getId().equals(userId)) {
            return userRepository.save(userEty);
        } else {
            throw new IllegalArgumentException("User ID mismatch");
        }
    }

    public void deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new IllegalArgumentException("User does not exist");
        }
        userRepository.deleteById(userId);
    }

    public void changeEmail(Long userId, String newEmail) {
        UserEty user = findById(userId);
        user.setEmail(newEmail);
        userRepository.save(user);
    }

    public void resetProfile(Long userId) {
        UserEty user = findById(userId);
        user.setName("Unknown");
        user.setEmail("User");
        user.setId(null);
        user.setWallet(null);
        userRepository.save(user);
    }

    public ResponseEntity<Void> createManyUsers(List<UserEty> users) {
        List<UserEty> newUsers = users.stream()
                .filter(u -> u.getId() == null)
                .toList();
        userRepository.saveAll(newUsers);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    public long countUsers() {
        return userRepository.count();
    }

    public UserEty findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new NoSuchElementException("User not found"));
    }

    public boolean userExists(Long id) {
        return userRepository.existsById(id);
    }
}
