package com.sam.profilecreation_service.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.sam.profilecreation_service.entity.UserEntity;
import com.sam.profilecreation_service.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserServiceImpl userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void testRegisterUser_Success() throws Exception {
        // Setup user entity
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("user");
        userEntity.setEmail("user@example.com");
        userEntity.setPassword("password");
        when(userService.signup(any(UserEntity.class))).thenReturn(userEntity);

        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(userEntity);

        mockMvc.perform(post("/api/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("User signed up successfully"));
    }

    @Test
    void testRegisterUser_EmailAlreadyExists() throws Exception {
        // Setup user entity
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("user");
        userEntity.setEmail("user@example.com");
        userEntity.setPassword("password");

        when(userService.signup(any(UserEntity.class))).thenThrow(new RuntimeException("Email already in use"));

        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(userEntity);

        mockMvc.perform(post("/api/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Error during signup: Email already in use"));
    }

    @Test
    void testLoginUser_Success() throws Exception {
        Map<String, String> loginRequest = new HashMap<>();
        loginRequest.put("username", "user");
        loginRequest.put("password", "password");

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("user");
        userEntity.setPassword("password");

        when(userService.login(anyString(), anyString())).thenReturn(userEntity);

        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(loginRequest);

        mockMvc.perform(post("/api/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Login successful"))
                .andExpect(jsonPath("$.user.username").value("user"));
    }

    @Test
    void testLoginUser_InvalidCredentials() throws Exception {
        // Setup login request data
        Map<String, String> loginRequest = new HashMap<>();
        loginRequest.put("username", "user");
        loginRequest.put("password", "wrongPassword");

        when(userService.login(anyString(), anyString())).thenThrow(new RuntimeException("Invalid username or password"));

        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(loginRequest);

        mockMvc.perform(post("/api/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message").value("Invalid credentials"));
    }

}
