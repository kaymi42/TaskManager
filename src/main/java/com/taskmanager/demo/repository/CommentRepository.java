package com.taskmanager.demo.repository;

import com.taskmanager.demo.entity.Comment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Long> {
    Optional<List<Comment>> findAllByAuthorId(Long id);
}
