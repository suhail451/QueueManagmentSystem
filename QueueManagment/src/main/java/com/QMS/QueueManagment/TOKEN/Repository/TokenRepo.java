package com.QMS.QueueManagment.TOKEN.Repository;

import com.QMS.QueueManagment.TOKEN.Entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface TokenRepo extends JpaRepository<Token,Long> {

}
