package com.QMS.QueueManagment.TOKEN.Entity;

import com.QMS.QueueManagment.QUEUE.Entity.Queue;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Token {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String status="Active";
    private  int tokenNo;
    private Date created_at=new Date();

    @ManyToOne
    @JoinColumn(name="queue_id",nullable = false)
    private Queue queue;




}
