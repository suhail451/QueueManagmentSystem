package com.QMS.QueueManagment.ADMIN.Entity;


import com.QMS.QueueManagment.QUEUE.Entity.Queue;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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

    @NotBlank(message = "User name can not be blank")
    @Column(nullable = false,unique = true)
    private String name;

    private String company;
    private String password;

    @OneToOne(mappedBy = "admin")
    @JsonBackReference
    private Queue queue;

}
