package com.sam.profilecreation_service.service.impl;

import com.sam.profilecreation_service.entity.*;
import com.sam.profilecreation_service.repository.AdminRepository;
import com.sam.profilecreation_service.repository.CoachRepository;
import com.sam.profilecreation_service.repository.PlayerRepository;
import com.sam.profilecreation_service.repository.UserRepository;
import com.sam.profilecreation_service.service.AdminService;
import com.sam.profilecreation_service.service.CoachService;
import com.sam.profilecreation_service.service.PlayerService;
import com.sam.profilecreation_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private CoachRepository coachRepository;

    @Autowired
    private PlayerService playerService;

    @Autowired
    private CoachService coachService;

    @Autowired
    private AdminService adminService;


    

    @Override
    public UserEntity signup(UserEntity userEntity) {
        System.out.println(userEntity);
        if (userRepository.existsByEmail(userEntity.getEmail())) {
            throw new RuntimeException("Email already in use");
        }
        if (userRepository.existsByUsername(userEntity.getUsername())) {
            throw new RuntimeException("Username already in use");
        }

        userEntity =userRepository.save(userEntity);
        System.out.println("After saving"+userEntity);
        switch (userEntity.getRole()) {

            case ADMIN:
                AdminEntity admin = new AdminEntity();
                admin.setUsername(userEntity.getUsername());
                admin.setEmail(userEntity.getEmail());
                admin.setPassword(userEntity.getPassword());
                admin.setRole(ERole.ADMIN);
                admin.setGender(userEntity.getGender());
                admin.setDateOfBirth(userEntity.getDateOfBirth());
                admin.setName(userEntity.getName());
                admin.setProfilePicture(userEntity.getProfilePicture());
                admin.setCountry(userEntity.getCountry());
                adminRepository.save(admin);
                break;

            case PLAYER:
                System.out.println("In case"+userEntity);
                PlayerEntity player = new PlayerEntity();
                player.setUsername(userEntity.getUsername());
                player.setEmail(userEntity.getEmail());
                player.setRole(ERole.PLAYER);
                player.setGender(userEntity.getGender());
                player.setDateOfBirth(userEntity.getDateOfBirth());
                player.setName(userEntity.getName());
                player.setProfilePicture(userEntity.getProfilePicture());
                player.setCountry(userEntity.getCountry());

               player.setSpecialization(userEntity.getSpecialization());
                player.setOverseas(false);
                player.setBackup(false);
                player.setPlaying(false);
                playerRepository.save(player);
                break;

            case COACH:
                CoachEntity coach = new CoachEntity();
                coach.setUsername(userEntity.getUsername());
                coach.setEmail(userEntity.getEmail());
                coach.setRole(ERole.COACH);
                coach.setGender(userEntity.getGender());
                coach.setDateOfBirth(userEntity.getDateOfBirth());
                coach.setName(userEntity.getName());
                coach.setProfilePicture(userEntity.getProfilePicture());
                coach.setCountry(userEntity.getCountry());
                coach.setSpecialization(null);
                coachRepository.save(coach);
                break;

            default:
                throw new RuntimeException("Invalid role");
        }

        return userEntity;
    }



    @Override
    public UserEntity login(String username, String password) {
        UserEntity userEntity = userRepository.findByUsername(username);
        if (userEntity != null && userEntity.getPassword().equals(password)) {
            return (userEntity);
        } else {
            throw new RuntimeException("Invalid username or password");
        }
    }
}
