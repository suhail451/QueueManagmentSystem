package com.QMS.QueueManagment.QUEUE.Service;

import com.QMS.QueueManagment.ADMIN.Entity.Admin;
import com.QMS.QueueManagment.ADMIN.Repository.AdminRepo;
import com.QMS.QueueManagment.ADMIN.Service.AdminService;
import com.QMS.QueueManagment.QUEUE.Entity.Queue;
import com.QMS.QueueManagment.QUEUE.Repository.QueueRepo;
import com.QMS.QueueManagment.exception.InvalidStateException;
import com.QMS.QueueManagment.exception.ResourceNotFoundException;
import com.QMS.QueueManagment.exception.UnauthorizedAccessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;


@Service
public class QueueService {


    private static final Logger logger= LoggerFactory.getLogger(QueueService.class);


    final AdminService adminService;
    final QueueRepo queueRepo;
    final AdminRepo adminRepo;
    final RedisTemplate<String,String> redisTemplate;

    public QueueService(AdminService adminService, QueueRepo queueRepo, AdminRepo adminRepo, RedisTemplate<String, String> redisTemplate) {
        this.adminService = adminService;
        this.queueRepo = queueRepo;
        this.adminRepo = adminRepo;
        this.redisTemplate = redisTemplate;
    }

//    Create Queue
    public Queue createQueue(Long adminId){

        Admin admin=adminService.findAdmin(adminId)
                .orElseThrow(()-> new ResourceNotFoundException("Admin not found"));

        if(admin.getQueue()!=null){
            throw new InvalidStateException("Queue already exists for this admin");
        }
        Queue queue=new Queue();
        queue.setAdmin(admin);
        queue=queueRepo.save(queue);
        admin.setQueue(queue);
            logger.info("Queue created successfully");
        return queue;
    }

//    Get Queue
    public Queue getQueueByAdmin(Long adminId){

        Admin currentAdmin = getCurrentAdmin();

        Queue queue=queueRepo.findByAdminId(adminId)
                .orElseThrow(()->new ResourceNotFoundException("Queue not found"));

        if(!queue.getAdmin().getId().equals(currentAdmin.getId())){
            throw new UnauthorizedAccessException("You do not have access to this queue");
        }

        return queue;

    }

//    Soft Delete Queue
    public void closeQueue(Long queueId){

        Admin currentAdmin = getCurrentAdmin();

        Queue myQueue=queueRepo.findById(queueId)
                .orElseThrow(()-> new ResourceNotFoundException("Queue not found"));

        if (!myQueue.getAdmin().getId().equals(currentAdmin.getId())) {
            throw new UnauthorizedAccessException("You do not own this queue");
        }

        if(!myQueue.getIsOpen()){
            throw new InvalidStateException("Queue is already closed");
        }

        myQueue.setIsOpen(false);


       queueRepo.save(myQueue);
       logger.info("Queue closed successfully");

    }


    public void OpenQueue(Long queueId){

        Admin currentAdmin = getCurrentAdmin();

        Queue myQueue=queueRepo.findById(queueId)
                .orElseThrow(()-> new ResourceNotFoundException("Queue not found"));

        if (!myQueue.getAdmin().getId().equals(currentAdmin.getId())) {
            throw new UnauthorizedAccessException("You do not own this queue");
        }

        if(myQueue.getIsOpen()){
            throw new InvalidStateException("Queue is already opened");
        }

        myQueue.setIsOpen(true);


        queueRepo.save(myQueue);
        logger.info("Queue Opened successfully");

    }


    //    Delete Queue
    public void deleteQueue(Long queueId){

        Admin currentAdmin = getCurrentAdmin();

        Queue queue = queueRepo.findById(queueId)
                .orElseThrow(() -> new ResourceNotFoundException("Queue not found"));

        if(!queue.getAdmin().getId().equals(currentAdmin.getId())){
            throw new UnauthorizedAccessException("You do not own this queue");
        }

        Admin admin = queue.getAdmin();
        admin.setQueue(null);   // break the back-reference first

        queueRepo.delete(queue);
        redisTemplate.delete("queue:" + queueId + ":counter");
        logger.info("Queue deleted from database");
    }

// for resource based check
    private Admin getCurrentAdmin(){

        String currentUsername = Objects.requireNonNull(
                SecurityContextHolder.getContext().getAuthentication()).getName();

        Admin admin = adminRepo.findByName(currentUsername);

        if (admin == null) {
            throw new ResourceNotFoundException("Authenticated admin not found");
        }

        return admin;
    }

}
