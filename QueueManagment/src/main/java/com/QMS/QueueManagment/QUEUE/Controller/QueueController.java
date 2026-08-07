package com.QMS.QueueManagment.QUEUE.Controller;

import com.QMS.QueueManagment.QUEUE.Entity.Queue;
import com.QMS.QueueManagment.QUEUE.Service.QueueService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class QueueController {

    final QueueService queueService;


    public QueueController(QueueService queueService) {
        this.queueService = queueService;
    }



    @PostMapping("/api/v1/queue/{id}")
    public ResponseEntity<Queue> createQueue(@PathVariable Long id){

        Queue queue=queueService.createQueue(id);
        return new ResponseEntity<>(queue, HttpStatus.CREATED);
    }

    @PutMapping("/api/v1/status/{id}")
    public ResponseEntity<String> closeQueue(@PathVariable Long id){

       Boolean myStatus= queueService.closeQueue(id);
        if(myStatus==true){
            throw new RuntimeException("Queue is not closed properly");

        }

        return new ResponseEntity<>("Queue Closed",HttpStatus.OK);
    }

    @DeleteMapping("/api/v1/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        queueService.deleteQueue(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }


}
