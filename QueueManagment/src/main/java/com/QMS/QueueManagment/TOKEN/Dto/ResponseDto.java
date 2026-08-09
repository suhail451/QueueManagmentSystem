package com.QMS.QueueManagment.TOKEN.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDto {

    private Long id;
    private int tokenNo;
    private String status;
    private Date createdAt;
    private Long queueId;

}
