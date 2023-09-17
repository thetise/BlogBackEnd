package com.project.smartICT.controllers;

import com.project.smartICT.responses.AuthResponse;
import com.project.smartICT.services.AdminService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admins")
public class AdminController {
    private AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @DeleteMapping("/{postId}")
    public AuthResponse deleteOnePostAdminAuthority(@PathVariable Long postId){
        return adminService.deleteOnePostAdminAuthority(postId);
    }
}
