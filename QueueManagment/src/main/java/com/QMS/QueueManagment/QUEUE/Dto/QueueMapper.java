package com.QMS.QueueManagment.QUEUE.Dto;

import com.QMS.QueueManagment.QUEUE.Entity.Queue;

public class QueueMapper {

    private QueueMapper() {
        // utility class, no instances
    }

    public static QueueResponseDto toDto(Queue queue) {
        return new QueueResponseDto(
                queue.getId(),
                queue.getIsOpen(),
                queue.getAdmin().getId()
        );
    }
}
