package com.QMS.QueueManagment.AUTH.Login;

import com.QMS.QueueManagment.ADMIN.Entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoginRepo extends JpaRepository<Admin, Long> {


    Optional<Admin> findByName(String name);

}
