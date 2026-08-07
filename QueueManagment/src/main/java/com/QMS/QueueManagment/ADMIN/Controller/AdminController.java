package com.QMS.QueueManagment.ADMIN.Controller;


import com.QMS.QueueManagment.ADMIN.Dto.AdminRequest;
import com.QMS.QueueManagment.ADMIN.Dto.AdminResponse;
import com.QMS.QueueManagment.ADMIN.Service.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class AdminController {

    final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }


    @PostMapping("/api/v1/admin")
    public ResponseEntity<AdminResponse> createAdmin(@RequestBody AdminRequest adminRequest){

        AdminResponse myAdmin=adminService.createAdmin(adminRequest);

        return new ResponseEntity<>(myAdmin, HttpStatus.CREATED);

    }

}
