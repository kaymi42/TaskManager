package com.taskmanager.demo.repository;

import com.taskmanager.demo.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    // select
    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);
}
