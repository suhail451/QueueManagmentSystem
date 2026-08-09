package com.QMS.QueueManagment.TOKEN.Controller;

import com.QMS.QueueManagment.TOKEN.Dto.ResponseDto;
import com.QMS.QueueManagment.TOKEN.Dto.TokenMapper;
import com.QMS.QueueManagment.TOKEN.Entity.Token;
import com.QMS.QueueManagment.TOKEN.Service.TokenService;
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

    @PostMapping("/queue/{queueId}")
    public ResponseEntity<ResponseDto> createToken(@PathVariable Long queueId){

        Token token=tokenService.createToken(queueId);

        return new ResponseEntity<>(TokenMapper.toDto(token), HttpStatus.CREATED);
    }


    @GetMapping("/queue/{queueId}")
    public ResponseEntity<List<ResponseDto>> tokenByQueue(@PathVariable Long queueId){
        List<ResponseDto> list = tokenService.getTokenByQueue(queueId)
                .stream()
                .map(TokenMapper::toDto)
                .toList();

        return new ResponseEntity<>(list,HttpStatus.OK);
    }


    @PatchMapping("/done/{tokenId}")
    public ResponseEntity<ResponseDto> markTokenDone(@PathVariable Long tokenId){

       return new ResponseEntity<>(TokenMapper.toDto(tokenService.markTokenDone(tokenId)),HttpStatus.OK);
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
