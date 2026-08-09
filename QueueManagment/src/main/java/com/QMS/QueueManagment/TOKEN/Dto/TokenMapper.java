package com.QMS.QueueManagment.TOKEN.Dto;

import com.QMS.QueueManagment.TOKEN.Entity.Token;

public class TokenMapper {

    private TokenMapper() {
        // utility class, no instances
    }

    public static ResponseDto toDto(Token token) {
        return new ResponseDto(
                token.getId(),
                token.getTokenNo(),
                token.getStatus(),
                token.getCreated_at(),
                token.getQueue().getId()
        );
    }
}
