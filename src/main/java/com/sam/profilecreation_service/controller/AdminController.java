package com.sam.profilecreation_service.controller;


import com.sam.profilecreation_service.entity.AdminEntity;
import com.sam.profilecreation_service.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin("http://localhost:3000")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/admins")
    public List<AdminEntity> getAllAdmins() {
        return adminService.getAllAdmin();
    }
}
