package com.sam.profilecreation_service.service.impl;


import com.sam.profilecreation_service.entity.AdminEntity;
import com.sam.profilecreation_service.repository.AdminRepository;
import com.sam.profilecreation_service.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService
{

    @Autowired
    private  AdminRepository adminRepository;

    @Override
    public List<AdminEntity> getAllAdmin (){
        System.out.println("testing this");
        return adminRepository.findAll();
    }
}
