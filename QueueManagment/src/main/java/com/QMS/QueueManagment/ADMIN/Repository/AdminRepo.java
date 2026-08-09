package com.QMS.QueueManagment.ADMIN.Repository;


import com.QMS.QueueManagment.ADMIN.Entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AdminRepo extends JpaRepository<Admin,Long> {


    Admin findByName(String name);
    boolean existsByName(String name);
}
