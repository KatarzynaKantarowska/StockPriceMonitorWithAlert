package com.kodilla.stockpricemonitorwithalert.controller;

import com.kodilla.stockpricemonitorwithalert.entity.UserEty;
import com.kodilla.stockpricemonitorwithalert.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class UserControllerTest {

    private UserService userService;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        userService = mock(UserService.class);
        UserController userController = new UserController(userService);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void findById() throws Exception {
        //Given

        UserEty userEty = new UserEty(1L, "KasiaKanta", "kasia@gmail.com", "123456789");
        when(userService.findById(any())).thenReturn(userEty);

        //When & Then
        mockMvc.perform(get("/api/v1/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("KasiaKanta"))
                .andExpect(jsonPath("$.email").value("kasia@gmail.com"))
                .andExpect(jsonPath("$.wallet").value("123456789"));
    }

    @Test
    void shouldReturnAll() throws Exception {
        //Given
        List<UserEty> users = List.of(
                new UserEty(1L, "Kasia", "kasia@gmail.com", "123456789"),
                new UserEty(2L, "Anna", "anna@gmail.com", "123456788")
        );
        when(userService.findAll()).thenReturn(users);
        //When & Then
        mockMvc.perform(get("/api/v1/users/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));

    }

    @Test
    void shouldCreateNewUser() throws Exception {
        //Given
        UserEty user = new UserEty(null, "Kasia", "kasia@gmail.com", "123456789");
        UserEty savedUser = new UserEty(1L, "Kasia", "kasia@gmail.com", "123456789");

        when(userService.createUser(any())).thenReturn(savedUser);
        //When & Then
        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "name": "Kasia",
                                  "email": "kasia@gmail.com",
                                  "wallet": "123456789"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Kasia"))
                .andExpect(jsonPath("$.email").value("kasia@gmail.com"))
                .andExpect(jsonPath("$.wallet").value("123456789"));
    }

    @Test
    void shouldUpdateUser() throws Exception {
        // Given
        UserEty updated = new UserEty(1L, "Kasia", "newMail@gmail.com", "123456789");
        when(userService.updateUser(eq(1L), any())).thenReturn(updated);

        // When & Then
        mockMvc.perform(put("/api/v1/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                    {
                                        "name": "Kasia",
                                        "email": "kasia@gmail.com",
                                        "wallet": "123456789"
                                    }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Kasia"));
    }


    @Test
    void shouldDeleteUser() throws Exception {
        // When & Then
        mockMvc.perform(delete("/api/v1/users/1"))
                .andExpect(status().isOk());

        verify(userService).deleteUser(1L);
    }

    @Test
    void shouldReturnUserCount() throws Exception {
        Mockito.when(userService.countUsers()).thenReturn(42L);

        mockMvc.perform(get("/api/v1/users/count"))
                .andExpect(status().isOk())
                .andExpect(content().string("42"));
    }
    @Test
    void shouldReturnTrueIfUserExists() throws Exception {
        Long id = 1L;

        Mockito.when(userService.userExists(id)).thenReturn(true);

        mockMvc.perform(get("/api/v1/users/" + id + "/exists"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }
    @Test
    void shouldReturnUserByEmail() throws Exception {
        String email = "kasia@gmail.com";
        UserEty user = new UserEty();
        user.setId(1L);
        user.setEmail(email);

        Mockito.when(userService.findByEmail(email)).thenReturn(user);

        mockMvc.perform(get("/api/v1/users/search").param("email", email))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(email));
    }
    @Test
    void shouldCreateManyUsers() throws Exception {
        List<UserEty> users = List.of(new UserEty());
        Mockito.when(userService.createManyUsers(users)).thenReturn(new ResponseEntity<>(HttpStatus.CREATED));

        mockMvc.perform(post("/api/v1/users/bulk").contentType(MediaType.APPLICATION_JSON)
                .content("""
                        [
                          {
                            "id": 1,
                            "name": "Kasia",
                            "email": "kasia@gmail.com",
                            "wallet": "12345-ABCDE"
                          }
                        ]
                        """))
                .andExpect(status().isCreated());
    }
    @Test
    void shouldChangeUserEmail() throws Exception {
        Long userId = 1L;
        String newEmail = "nowy@example.com";

        mockMvc.perform(put("/api/v1/users/{id}/email", userId)
                        .param("email", newEmail))
                .andExpect(status().isNoContent());

        Mockito.verify(userService).changeEmail(userId, newEmail);
    }
}