package com.QMS.QueueManagment.TOKEN.Repository;

import com.QMS.QueueManagment.TOKEN.Entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface TokenRepo extends JpaRepository<Token,Long> {

    @Query("SELECT MAX(t.tokenNo) FROM Token t WHERE t.queue.id = :queueId")
    Integer findMaxTokenNoByQueueId(@Param("queueId") Long queueId);

    Token findByTokenNo(int tokenNo);

    List<Token> findTokenByQueueId(Long queueId);


    long countByQueueIdAndStatusAndTokenNoLessThan(Long queueId, String status, int tokenNo);
}
