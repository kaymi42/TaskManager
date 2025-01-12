package com.taskmanager.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Collection;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String username;
    private String password;
    private String email;

    @ManyToMany
    private Collection<Role> roles;

    public User(){}

    public User(String username){
        this.setUsername(username);
    }
}
