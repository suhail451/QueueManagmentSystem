package com.QMS.QueueManagment.TOKEN.Service;


import com.QMS.QueueManagment.QUEUE.Entity.Queue;
import com.QMS.QueueManagment.QUEUE.Repository.QueueRepo;
import com.QMS.QueueManagment.TOKEN.Entity.Token;
import com.QMS.QueueManagment.TOKEN.Repository.TokenRepo;
import com.QMS.QueueManagment.exception.InvalidStateException;
import com.QMS.QueueManagment.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TokenService {

    private static final Logger logger= LoggerFactory.getLogger(TokenService.class);

    final QueueRepo queueRepo;
    final TokenRepo tokenRepo;
    final RedisTemplate<String,String> redisTemplate;

    public TokenService(QueueRepo queueRepo, TokenRepo tokenRepo, RedisTemplate<String,String> redisTemplate) {
        this.queueRepo = queueRepo;
        this.tokenRepo = tokenRepo;
        this.redisTemplate = redisTemplate;
    }

//    Create token
    public Token createToken(Long queueId){

        Queue queue=queueRepo.findById(queueId)
                .orElseThrow(()-> new ResourceNotFoundException("Queue not found"));

        if(!queue.getIsOpen()){
            throw new InvalidStateException("Queue is closed");
        }

        String key="queue:"+queueId+":counter";
        Boolean exists = redisTemplate.hasKey(key);

        if (Boolean.FALSE.equals(exists)) {

            Integer maxTokenNo = tokenRepo.findMaxTokenNoByQueueId(queueId);

            int currentCounter = (maxTokenNo == null) ? 0 : maxTokenNo;

            redisTemplate.opsForValue().setIfAbsent(key, String.valueOf(currentCounter));
        }

        int nextToken=redisTemplate.opsForValue().increment(key).intValue();

        Token token=new Token();
        token.setQueue(queue);
        token.setTokenNo(nextToken);
        token.setStatus("Waiting");

        return tokenRepo.save(token);
    }

//    Mark token done
    public Token markTokenDone(Long tokenId){
       Token token= tokenRepo.findById(tokenId)
               .orElseThrow(()->new ResourceNotFoundException("Token not found"));

       if(token.getStatus().equals("Waiting")){
           token.setStatus("Done");
       }

       Token markedToken= tokenRepo.save(token);
       logger.info("Token marked done!");
       return markedToken;
    }

//    Get Token
    public List<Token> getTokenByQueue(Long queueId){

        return tokenRepo.findTokenByQueueId(queueId);
    }

//    Mark token InActive
    public void markTokenInActive(Long tokenId){

       Token token = tokenRepo.findById(tokenId)
               .orElseThrow(() -> new ResourceNotFoundException("Token not found"));
       token.setStatus("InActive");
       tokenRepo.save(token);
    }

//    Find Position
    public Long findPosition(Long tokenId){

        Token token = tokenRepo.findById(tokenId)
                .orElseThrow(() -> new ResourceNotFoundException("Token not found"));

      return  tokenRepo.countByQueueIdAndStatusAndTokenNoLessThan(
              token.getQueue().getId(), "Waiting", token.getTokenNo());
    }

}
