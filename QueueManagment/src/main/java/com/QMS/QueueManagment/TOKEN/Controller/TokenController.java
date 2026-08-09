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
@RequestMapping("/api/v1/token")
public class TokenController {

    final TokenService tokenService;

    public TokenController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @PostMapping("/{queueId}")
    public ResponseEntity<Token> createToken(@PathVariable Long queueId){

        Token token=tokenService.createToken(queueId);

        return new ResponseEntity<>(token, HttpStatus.CREATED);
    }


    @GetMapping("/{queueId}")
    public ResponseEntity<List<Token>> tokenByQueue(@PathVariable Long queueId){
        List<Token> list=tokenService.getTokenByQueue(queueId);

        return new ResponseEntity<>(list,HttpStatus.OK);
    }


    @PatchMapping("/done/{tokenId}")
    public ResponseEntity<Token> markTokenDone(@PathVariable Long tokenId){

       return new ResponseEntity<>(tokenService.markTokenDone(tokenId),HttpStatus.OK);
    }


    @PatchMapping("/leave/{tokenId}")
    public ResponseEntity<Void>  markTokenInActive(@PathVariable Long tokenId){

        tokenService.markTokenInActive(tokenId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @GetMapping("/position/{tokenId}")
    public ResponseEntity<Long> findPosition(@PathVariable Long tokenId){

        Long position=tokenService.findPosition(tokenId);

        return new ResponseEntity<>(position,HttpStatus.OK);

    }



}
