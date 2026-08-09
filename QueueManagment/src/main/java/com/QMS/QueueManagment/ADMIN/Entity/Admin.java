package com.QMS.QueueManagment.ADMIN.Entity;


import com.QMS.QueueManagment.QUEUE.Entity.Queue;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String company;
    private String password;

    @OneToOne(mappedBy = "admin")
    @JsonBackReference
    private Queue queue;

}
