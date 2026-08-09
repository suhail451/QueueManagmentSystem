package com.QMS.QueueManagment.TOKEN.Service;


import com.QMS.QueueManagment.QUEUE.Entity.Queue;
import com.QMS.QueueManagment.QUEUE.Repository.QueueRepo;
import com.QMS.QueueManagment.TOKEN.Entity.Token;
import com.QMS.QueueManagment.TOKEN.Repository.TokenRepo;
import com.QMS.QueueManagment.exception.InvalidStateException;
import com.QMS.QueueManagment.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TokenService {

    final QueueRepo queueRepo;
    final TokenRepo tokenRepo;

    public TokenService(QueueRepo queueRepo, TokenRepo tokenRepo) {
        this.queueRepo = queueRepo;
        this.tokenRepo = tokenRepo;
    }

//    Create token
    public Token createToken(Long queueId){

        Queue queue=queueRepo.findById(queueId)
                .orElseThrow(()-> new ResourceNotFoundException("Queue not found"));

        if(!queue.getIsOpen()){
            throw new InvalidStateException("Queue is closed");
        }

        Integer maxTokenNo=tokenRepo.findMaxTokenNoByQueueId(queueId);

        int nextToken=(maxTokenNo==null)?1:maxTokenNo+1;

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

       return tokenRepo.save(token);
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
