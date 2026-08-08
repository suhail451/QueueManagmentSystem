package com.QMS.QueueManagment.TOKEN.Service;


import com.QMS.QueueManagment.QUEUE.Entity.Queue;
import com.QMS.QueueManagment.QUEUE.Repository.QueueRepo;
import com.QMS.QueueManagment.TOKEN.Entity.Token;
import com.QMS.QueueManagment.TOKEN.Repository.TokenRepo;
import org.springframework.data.repository.core.support.RepositoryMethodInvocationListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
                .orElseThrow(()-> new RuntimeException("Queue not found"));
        if(queue.getIsOpen()==false){
            throw new RuntimeException("Queue is closed ");
        }

        Integer MaxToken_no=tokenRepo.findMaxTokenNoByQueueId(queueId);

        int nextToken=(MaxToken_no==null)?1:MaxToken_no+1;

        Token token=new Token();
        token.setQueue(queue);
        token.setTokenNo(nextToken);
        token.setStatus("Waiting");

        return tokenRepo.save(token);


    }

//    Mark token done

    public Token markTokenDone(Long tokenId){
       Token myToken= tokenRepo.findById(tokenId)
               .orElseThrow(()->new RuntimeException("Not found"));

       if(myToken.getStatus().equals("Waiting")){
           myToken.setStatus("Done");

       }

       return tokenRepo.save(myToken);

    }

//    Get Token

    public List<Token> getTokenByQueue(Long queueId){

        return tokenRepo.findTokenByQueueId(queueId);

    }


//Mark token InActive
    public void markTokenInActive(Long tokenId){

       Token mytoken = tokenRepo.findById(tokenId)
               .orElseThrow(() -> new RuntimeException("Token Notfound"));
       mytoken.setStatus("InActive");
       tokenRepo.save(mytoken);

    }

//    Find Position
    public Long findPosition(Long tokenId){

        Token token = tokenRepo.findById(tokenId)
                .orElseThrow(() -> new RuntimeException("Token not found"));


      return  tokenRepo.countByQueueIdAndStatusAndTokenNoLessThan(
              token.getQueue().getId(), "Waiting",token.getTokenNo());



    }



}
