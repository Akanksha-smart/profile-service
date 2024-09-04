package com.sam.profilecreation_service.service;


import com.sam.profilecreation_service.entity.UserEntity;

public interface UserService {
    UserEntity login(String username, String password);
    UserEntity signup(UserEntity UserEntity);
}
