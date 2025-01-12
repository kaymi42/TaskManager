package com.taskmanager.demo.repository;

import com.taskmanager.demo.entity.Task;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends CrudRepository<Task, Long> {
    Optional<List<Task>> findAllByAuthorId(Long id);
}
