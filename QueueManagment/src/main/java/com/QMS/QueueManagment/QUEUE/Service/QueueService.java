package com.QMS.QueueManagment.QUEUE.Service;

import com.QMS.QueueManagment.ADMIN.Entity.Admin;
import com.QMS.QueueManagment.ADMIN.Repository.AdminRepo;
import com.QMS.QueueManagment.ADMIN.Service.AdminService;
import com.QMS.QueueManagment.QUEUE.Entity.Queue;
import com.QMS.QueueManagment.QUEUE.Repository.QueueRepo;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;


@Service
public class QueueService {

    final AdminService adminService;
    final QueueRepo queueRepo;
    final AdminRepo adminRepo;

    public QueueService(AdminService adminService, QueueRepo queueRepo, AdminRepo adminRepo) {
        this.adminService = adminService;
        this.queueRepo = queueRepo;
        this.adminRepo = adminRepo;
    }

//    Create Queue
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

//    Get Queue
    public Queue getQueue(Long adminId){

        String currentUsername=Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getName();

        Admin currentAdmin=adminRepo.findByName(currentUsername);
        Queue queue=queueRepo.findByAdminId(adminId)
                .orElseThrow(()->new RuntimeException("Queue not found"));

        if(!queue.getAdmin().getId().equals(currentAdmin.getId())){
            throw new RuntimeException("access denied");
        }


        return queue;

    }

//    Soft Delete Queue
    public Boolean closeQueue(Long queueId){

        String currentUsername= Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getName();


        Admin currentAdmin=adminRepo.findByName(currentUsername);

        Queue myQueue=queueRepo.findById(queueId)
                .orElseThrow(()-> new RuntimeException("Queue not found"));

        if (!myQueue.getAdmin().getId().equals(currentAdmin.getId())) {
            throw new RuntimeException("You do not own this queue");
        }

        if(myQueue.getIsOpen()== false){

            throw new RuntimeException("Queue is already closed");

        }
        myQueue.setIsOpen(false);

        return queueRepo.save(myQueue).getIsOpen();
    }

//    Delete Queue
    public void deleteQueue(Long queueId){

        String currentUsername= Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getName();

        Admin currentAdmin=adminRepo.findByName(currentUsername);


        Queue queue = queueRepo.findById(queueId)
                .orElseThrow(() -> new RuntimeException("Queue not found"));

        if(!queue.getAdmin().getId().equals(currentAdmin.getId())){
            throw new RuntimeException("acces denied");
        }

        Admin admin = queue.getAdmin();
        admin.setQueue(null);   // break the back-reference first

        queueRepo.delete(queue);
    }


}
