package com.sam.profilecreation_service.controller;

import com.sam.profilecreation_service.entity.AdminEntity;
import com.sam.profilecreation_service.service.AdminService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import static org.mockito.Mockito.when;

@Nested
@SpringBootTest
class AdminControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AdminService adminService;

    @InjectMocks
    private AdminController adminController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(adminController).build();
    }

    @Test
    void testGetAllAdmins_Success() throws Exception {
        AdminEntity admin1 = new AdminEntity();
        AdminEntity admin2 = new AdminEntity();
        List<AdminEntity> expectedAdmins = Arrays.asList(admin1, admin2);

        when(adminService.getAllAdmin()).thenReturn(expectedAdmins);

        mockMvc.perform(get("/api/admins"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2));

        verify(adminService, times(1)).getAllAdmin();
    }

    @Test
    void testGetAllAdmins_EmptyList() throws Exception {
        when(adminService.getAllAdmin()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/admins"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(0));

        verify(adminService, times(1)).getAllAdmin();
    }

}
