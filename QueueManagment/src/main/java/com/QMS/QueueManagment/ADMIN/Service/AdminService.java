package com.QMS.QueueManagment.ADMIN.Service;


import com.QMS.QueueManagment.ADMIN.Dto.AdminRequest;
import com.QMS.QueueManagment.ADMIN.Dto.AdminResponse;
import com.QMS.QueueManagment.ADMIN.Entity.Admin;
import com.QMS.QueueManagment.ADMIN.Repository.AdminRepo;
import com.QMS.QueueManagment.exception.DuplicateUserNameException;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminService {


    private static final Logger logger= LoggerFactory.getLogger(AdminService.class);

    final AdminRepo adminRepo;
    private final PasswordEncoder passwordEncoder;

    public AdminService(AdminRepo adminRepo, PasswordEncoder passwordEncoder) {
        this.adminRepo = adminRepo;
        this.passwordEncoder = passwordEncoder;
    }


    public AdminResponse createAdmin(AdminRequest adminRequest){

        if(adminRepo.existsByName(adminRequest.getName())){
            throw new DuplicateUserNameException("User name already exist :");

        }

        Admin admin=new Admin();
        admin.setName(adminRequest.getName());
        admin.setCompany(adminRequest.getCompany());

        String hashedPassword=passwordEncoder.encode(adminRequest.getPassword());
        admin.setPassword(hashedPassword);

        logger.info("Admin created successfully");
          Admin myadmin=adminRepo.save(admin);

        AdminResponse adminResponse=new AdminResponse();
        adminResponse.setName(myadmin.getName());
        adminResponse.setCompany(admin.getCompany());
        adminResponse.setId(admin.getId());

        return adminResponse;

    }

    public Optional<Admin> findAdmin(Long id){

        return adminRepo.findById(id);
    }



}
