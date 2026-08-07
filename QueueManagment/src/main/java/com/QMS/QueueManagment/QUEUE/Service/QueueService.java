package com.QMS.QueueManagment.QUEUE.Service;

import com.QMS.QueueManagment.ADMIN.Entity.Admin;
import com.QMS.QueueManagment.ADMIN.Service.AdminService;
import com.QMS.QueueManagment.QUEUE.Entity.Queue;
import com.QMS.QueueManagment.QUEUE.Repository.QueueRepo;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class QueueService {

    final AdminService adminService;
    final QueueRepo queueRepo;

    public QueueService(AdminService adminService, QueueRepo queueRepo) {
        this.adminService = adminService;
        this.queueRepo = queueRepo;
    }

    public Queue createQueue(Long adminId){
        Admin admin=adminService.findAdmin(adminId)
                .orElseThrow(()-> new RuntimeException("Admin not found"));

        if(admin.getQueue()!=null){
            throw new RuntimeException("Queue Already exist");

        }

        Queue queue=new Queue();
        queue.setAdmin(admin);
        queue=queueRepo.save(queue);
        admin.setQueue(queue);

        return queue;

    }

    public Queue getQueue(Long adminId){

        return queueRepo.findByAdminId(adminId)
                .orElseThrow(()-> new RuntimeException("Queue Not found"));


    }

    public Boolean closeQueue(Long queueId){

        Queue myQueue=queueRepo.findById(queueId)
                .orElseThrow(()-> new RuntimeException("Queue not found"));

        if(myQueue.getIsOpen()== false){

            throw new RuntimeException("Queue is already closed");

        }
        myQueue.setIsOpen(false);

        return queueRepo.save(myQueue).getIsOpen();


    }

    public void deleteQueue(Long queueId){
        Queue queue = queueRepo.findById(queueId)
                .orElseThrow(() -> new RuntimeException("Queue not found"));

        Admin admin = queue.getAdmin();
        admin.setQueue(null);   // break the back-reference first

        queueRepo.delete(queue);
    }


}
