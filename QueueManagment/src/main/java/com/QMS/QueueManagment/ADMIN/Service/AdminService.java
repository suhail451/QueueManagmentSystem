package com.QMS.QueueManagment.ADMIN.Service;


import com.QMS.QueueManagment.ADMIN.Dto.AdminRequest;
import com.QMS.QueueManagment.ADMIN.Dto.AdminResponse;
import com.QMS.QueueManagment.ADMIN.Entity.Admin;
import com.QMS.QueueManagment.ADMIN.Repository.AdminRepo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminService {

    final AdminRepo adminRepo;
    private final PasswordEncoder passwordEncoder;

    public AdminService(AdminRepo adminRepo, PasswordEncoder passwordEncoder) {
        this.adminRepo = adminRepo;
        this.passwordEncoder = passwordEncoder;
    }


    public AdminResponse createAdmin(AdminRequest adminRequest){

        Admin admin=new Admin();
        admin.setName(adminRequest.getName());
        admin.setCompony(adminRequest.getCompony());

        String hashedPassword=passwordEncoder.encode(adminRequest.getPassword());
        admin.setPassword(hashedPassword);

          Admin myadmin=adminRepo.save(admin);

        AdminResponse adminResponse=new AdminResponse();
        adminResponse.setName(myadmin.getName());
        adminResponse.setCompony(admin.getCompony());
        adminResponse.setId(admin.getId());

        return adminResponse;

    }

    public Optional<Admin> findAdmin(Long id){

        return adminRepo.findById(id);
    }



}
