package com.QMS.QueueManagment.QUEUE.Repository;

import com.QMS.QueueManagment.QUEUE.Entity.Queue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QueueRepo extends JpaRepository<Queue,Long> {

     Optional<Queue> findByAdminId(Long admin_id);
}
