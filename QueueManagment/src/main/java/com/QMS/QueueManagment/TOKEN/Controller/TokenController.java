package com.QMS.QueueManagment.TOKEN.Controller;

import com.QMS.QueueManagment.TOKEN.Entity.Token;
import com.QMS.QueueManagment.TOKEN.Repository.TokenRepo;
import com.QMS.QueueManagment.TOKEN.Service.TokenService;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TokenController {

    final TokenService tokenService;

    public TokenController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @PostMapping("/token/{queueId}")
    public ResponseEntity<Token> createToken(@PathVariable Long queueId){

        Token mytoken=tokenService.createToken(queueId);
        return new ResponseEntity<>(mytoken, HttpStatus.CREATED);

    }

    @PatchMapping("/done/{id}")
    public ResponseEntity<Token> markTokenDone(@PathVariable int id){

       return new ResponseEntity<>(tokenService.markTokenDone(id),HttpStatus.OK);


    }

    @GetMapping("/token/{queueId}")
    public ResponseEntity<List<Token>> tokenByQueue(@PathVariable Long queueId){
        List<Token> list=tokenService.getTokenByQueue(queueId);
        return new ResponseEntity<>(list,HttpStatus.OK);
    }

    @DeleteMapping("/{tokenNo}")
    public ResponseEntity<Void>  deleteByTokenNo(@PathVariable int tokenNo){

        tokenService.deleteToken(tokenNo);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);


    }

    @GetMapping("Position/{tokenId}")
    public ResponseEntity<Long> findPosition(@PathVariable Long tokenId){

        Long Position=tokenService.findPosition(tokenId);

        return new ResponseEntity<>(Position,HttpStatus.OK);

    }



}
