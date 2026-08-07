package com.QMS.QueueManagment.TOKEN.Controller;

import com.QMS.QueueManagment.TOKEN.Entity.Token;
import com.QMS.QueueManagment.TOKEN.Repository.TokenRepo;
import com.QMS.QueueManagment.TOKEN.Service.TokenService;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

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


}
