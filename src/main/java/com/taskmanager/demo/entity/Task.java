package com.taskmanager.demo.entity;

import com.taskmanager.demo.entity.enums.Priority;
import com.taskmanager.demo.entity.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.ORDINAL)
    private Status status;

    @Enumerated(EnumType.ORDINAL)
    private Priority priority;

    private Date creationDate;
    private String title;

    @ManyToOne
    private User author;

    @ManyToMany
    private Collection<User> executors;

    @OneToMany
    private Collection<Comment> comments;
}
