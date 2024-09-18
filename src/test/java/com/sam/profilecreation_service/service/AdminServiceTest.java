package com.sam.profilecreation_service.service;


import com.sam.profilecreation_service.entity.AdminEntity;
import com.sam.profilecreation_service.repository.AdminRepository;
import com.sam.profilecreation_service.service.impl.AdminServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class AdminServiceTest {




        @Mock
        private AdminRepository adminRepository;

        @InjectMocks
        private AdminServiceImpl adminService;

        @BeforeEach
        void setUp() {
            MockitoAnnotations.openMocks(this);
        }

        @Test
        void testGetAllAdmin_Success() {

            AdminEntity admin1 = new AdminEntity();
            AdminEntity admin2 = new AdminEntity();
            List<AdminEntity> expectedAdmins = Arrays.asList(admin1, admin2);

            when(adminRepository.findAll()).thenReturn(expectedAdmins);

            List<AdminEntity> result = adminService.getAllAdmin();

            assertNotNull(result);
            assertEquals(2, result.size());
            assertEquals(expectedAdmins, result);
            verify(adminRepository, times(1)).findAll();
        }

        @Test
        void testGetAllAdmin_EmptyList() {
            when(adminRepository.findAll()).thenReturn(Collections.emptyList());

            List<AdminEntity> result = adminService.getAllAdmin();

            assertNotNull(result);
            assertTrue(result.isEmpty());
            verify(adminRepository, times(1)).findAll();
        }

        @Test
        void testGetAllAdmin_PrintStatement() {
            when(adminRepository.findAll()).thenReturn(Collections.emptyList());
            ByteArrayOutputStream outContent = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outContent));

            adminService.getAllAdmin();

            assertEquals("testing this" + System.lineSeparator(), outContent.toString());

            System.setOut(System.out);
        }

}
