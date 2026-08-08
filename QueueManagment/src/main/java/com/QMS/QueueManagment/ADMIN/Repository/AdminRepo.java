package com.QMS.QueueManagment.ADMIN.Repository;


import com.QMS.QueueManagment.ADMIN.Entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepo extends JpaRepository<Admin,Long> {


    Admin findByName(String name);
}
