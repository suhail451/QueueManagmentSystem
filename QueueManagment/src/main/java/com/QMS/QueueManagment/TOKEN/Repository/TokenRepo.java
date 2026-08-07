package com.QMS.QueueManagment.TOKEN.Repository;

import com.QMS.QueueManagment.TOKEN.Entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository

public interface TokenRepo extends JpaRepository<Token,Long> {

    @Query("SELECT MAX(t.tokenNo) FROM Token t WHERE t.queue.id = :queueId")
    Integer findMaxTokenNoByQueueId(@Param("queueId") Long queueId);

}
