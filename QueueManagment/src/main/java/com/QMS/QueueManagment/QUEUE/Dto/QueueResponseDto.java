package com.QMS.QueueManagment.QUEUE.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QueueResponseDto {

    private Long id;
    private Boolean isOpen;
    private Long adminId;

}
