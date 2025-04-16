package com.kodilla.stockpricemonitorwithalert.service;

import com.kodilla.stockpricemonitorwithalert.entity.UserEty;
import com.kodilla.stockpricemonitorwithalert.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {
    private UserRepository userRepository;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        userService = new UserService(userRepository);
    }

    @Test
    void shouldFindById() {
        // Given
        UserEty user = new UserEty();
        user.setId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // When
        UserEty result = userService.findById(1L);

        // Then
        assertEquals(1L, result.getId());
    }

    @Test
    void shouldReturnOptional() {
        // Given
        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(NoSuchElementException.class, () -> userService.findById(999L));
    }

    @Test
    void shouldFindAll () {
        // Given
        List<UserEty> users = List.of(new UserEty(), new UserEty());
        when(userRepository.findAll()).thenReturn(users);

        // When
        List<UserEty> result = userService.findAll();

        // Then
        assertEquals(2, result.size());
    }

    @Test
    void shouldCreateUser(){
        // Given
        UserEty newUser = new UserEty();
        when(userRepository.save(newUser)).thenReturn(newUser);

        // When
        UserEty result = userService.createUser(newUser);

        // Then
        assertEquals(newUser, result);
    }

    @Test
    void shouldUpdateUser() {
        // Given
        UserEty user = new UserEty();
        user.setId(1L);
        when(userRepository.save(user)).thenReturn(user);

        // When
        UserEty result = userService.updateUser(1L, user);

        // Then
        assertEquals(user, result);
    }

    @Test
    void shouldDeleteUser() {
        // Given
        when(userRepository.existsById(1L)).thenReturn(true);

        // When
        userService.deleteUser(1L);

        // Then
        verify(userRepository).deleteById(1L);
    }

    @Test
    void shouldCountUsers() {
        // Given
        when(userRepository.count()).thenReturn(5L);

        // When
        long count = userService.countUsers();

        // Then
        assertEquals(5L, count);
    }

    @Test
    void shouldFindByEmail() {
        // Given
        String email = "test@example.com";
        UserEty user = new UserEty();
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        // When
        UserEty result = userService.findByEmail(email);

        // Then
        assertEquals(user, result);
    }

    @Test
    void shouldCheckIfUserExists() {
        // Given
        when(userRepository.existsById(1L)).thenReturn(true);

        // When & Then
        assertTrue(userService.userExists(1L));
    }
}