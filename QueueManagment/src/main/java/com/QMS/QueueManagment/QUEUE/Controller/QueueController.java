package com.QMS.QueueManagment.QUEUE.Controller;

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



    @PostMapping("/{id}")
    public ResponseEntity<Queue> createQueue(@PathVariable Long id){

        Queue queue=queueService.createQueue(id);
        return new ResponseEntity<>(queue, HttpStatus.CREATED);
    }

    @GetMapping("/{adminId}")
    public ResponseEntity<Queue> getQueue(@PathVariable Long adminId){
        Queue queue = queueService.getQueueByAdmin(adminId);
        return new ResponseEntity<>(queue, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> closeQueue(@PathVariable Long id){

       Boolean isQueueClosed= queueService.closeQueue(id);
        if(isQueueClosed){
            throw new RuntimeException("Queue is not closed properly");

        }

        return new ResponseEntity<>("Queue Closed",HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        queueService.deleteQueue(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }


}
