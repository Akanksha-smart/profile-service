package com.sam.profilecreation_service.service;
import com.sam.profilecreation_service.entity.*;
import com.sam.profilecreation_service.repository.*;
import com.sam.profilecreation_service.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private AdminRepository adminRepository;

    @Mock
    private PlayerRepository playerRepository;

    @Mock
    private CoachRepository coachRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSignup_Admin() {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("admin");
        userEntity.setEmail("admin@example.com");
        userEntity.setRole(ERole.ADMIN);

        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);

        AdminEntity adminEntity = new AdminEntity();
        when(adminRepository.save(any(AdminEntity.class))).thenReturn(adminEntity);

        UserEntity savedUser = userService.signup(userEntity);

        assertNotNull(savedUser);
        verify(adminRepository, times(1)).save(any(AdminEntity.class));
        verify(playerRepository, never()).save(any(PlayerEntity.class));
        verify(coachRepository, never()).save(any(CoachEntity.class));
    }

    @Test
    void testSignup_Player() {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("player");
        userEntity.setEmail("player@example.com");
        userEntity.setRole(ERole.PLAYER);

        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);

        PlayerEntity playerEntity = new PlayerEntity();
        when(playerRepository.save(any(PlayerEntity.class))).thenReturn(playerEntity);

        UserEntity savedUser = userService.signup(userEntity);

        assertNotNull(savedUser);
        verify(playerRepository, times(1)).save(any(PlayerEntity.class));
        verify(adminRepository, never()).save(any(AdminEntity.class));
        verify(coachRepository, never()).save(any(CoachEntity.class));
    }

    @Test
    void testSignup_Coach() {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("coach");
        userEntity.setEmail("coach@example.com");
        userEntity.setRole(ERole.COACH);

        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);

        CoachEntity coachEntity = new CoachEntity();
        when(coachRepository.save(any(CoachEntity.class))).thenReturn(coachEntity);

        UserEntity savedUser = userService.signup(userEntity);

        assertNotNull(savedUser);
        verify(coachRepository, times(1)).save(any(CoachEntity.class));
        verify(adminRepository, never()).save(any(AdminEntity.class));
        verify(playerRepository, never()).save(any(PlayerEntity.class));
    }

    @Test
    void testSignup_EmailAlreadyExists() {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("user");
        userEntity.setEmail("user@example.com");

        when(userRepository.existsByEmail(anyString())).thenReturn(true);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> userService.signup(userEntity));
        assertEquals("Email already in use", exception.getMessage());
        verify(userRepository, never()).save(any(UserEntity.class));
    }

    @Test
    void testSignup_UsernameAlreadyExists() {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("user");
        userEntity.setEmail("user@example.com");

        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(userRepository.existsByUsername(anyString())).thenReturn(true);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> userService.signup(userEntity));
        assertEquals("Username already in use", exception.getMessage());
        verify(userRepository, never()).save(any(UserEntity.class));
    }

    @Test
    void testLogin_Success() {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("user");
        userEntity.setPassword("password");

        when(userRepository.findByUsername(anyString())).thenReturn(userEntity);

        UserEntity loggedInUser = userService.login("user", "password");

        assertNotNull(loggedInUser);
        assertEquals("user", loggedInUser.getUsername());
    }

    @Test
    void testLogin_InvalidCredentials() {
        when(userRepository.findByUsername(anyString())).thenReturn(null);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> userService.login("user", "wrongPassword"));
        assertEquals("Invalid username or password", exception.getMessage());
    }
}
