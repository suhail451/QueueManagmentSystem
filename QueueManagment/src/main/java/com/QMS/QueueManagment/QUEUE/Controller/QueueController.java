package com.QMS.QueueManagment.QUEUE.Controller;

import com.QMS.QueueManagment.QUEUE.Dto.QueueMapper;
import com.QMS.QueueManagment.QUEUE.Dto.QueueResponseDto;
import com.QMS.QueueManagment.QUEUE.Entity.Queue;
import com.QMS.QueueManagment.QUEUE.Service.QueueService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/queue")
public class QueueController {

    final QueueService queueService;


    public QueueController(QueueService queueService) {
        this.queueService = queueService;
    }



    @PostMapping("/admin{adminId}")
    public ResponseEntity<QueueResponseDto> createQueue(@PathVariable Long adminId){

        Queue queue=queueService.createQueue(adminId);
        return new ResponseEntity<>(QueueMapper.toDto(queue), HttpStatus.CREATED);
    }

    @GetMapping("/admin/{adminId}")
    public ResponseEntity<QueueResponseDto> getQueue(@PathVariable Long adminId){
        Queue queue = queueService.getQueueByAdmin(adminId);
        return new ResponseEntity<>(QueueMapper.toDto(queue), HttpStatus.OK);
    }

    @PatchMapping("/{id}/close")
    public ResponseEntity<String> closeQueue(@PathVariable Long id){

       queueService.closeQueue(id);

        return new ResponseEntity<>("Queue Closed",HttpStatus.OK);
    }


    @PatchMapping("/{id}/open")
    public ResponseEntity<String> openQueue(@PathVariable Long id){

        queueService.OpenQueue(id);

        return new ResponseEntity<>("Queue Opened",HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        queueService.deleteQueue(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }


}
