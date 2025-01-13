package com.taskmanager.demo.service.impl;

import com.taskmanager.demo.dto.AppError;
import com.taskmanager.demo.dto.TaskDto;
import com.taskmanager.demo.entity.Comment;
import com.taskmanager.demo.entity.Task;
import com.taskmanager.demo.entity.User;
import com.taskmanager.demo.entity.enums.Priority;
import com.taskmanager.demo.entity.enums.Status;
import com.taskmanager.demo.exception.AccessDeniedException;
import com.taskmanager.demo.exception.ResourceNotFoundException;
import com.taskmanager.demo.repository.CommentRepository;
import com.taskmanager.demo.repository.TaskRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class TaskServiceImpl {
    private TaskRepository taskRepository;
    private UserServiceImpl userService;

    // TODO: change to commentService
    private CommentRepository commentRepository;

    public List<Task> getAllTasks() {
        List<Task> allTasks = new ArrayList<>();
        taskRepository.findAll().forEach(allTasks::add);
        return allTasks;
    }


    public Task createTask(TaskDto taskDto, String authorName) {
        User author = userService.findByUsername(authorName);

        Task task = new Task();
        task.setTitle(taskDto.getTitle());
        task.setStatus(Status.valueOf(taskDto.getStatus()));
        task.setPriority(Priority.valueOf(taskDto.getPriority()));
        task.setAuthor(author);
        task.setStatus(Status.WAITING);
        task.setPriority(Priority.LOW);
        task.setCreationDate(new Date());

        return taskRepository.save(task);
    }


    public ResponseEntity<?> updateTaskById(Long id, TaskDto taskDto, String authorName) {
        User user = userService.findByUsername(authorName);
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task", "id", id));
        if (user.getRoles().stream().anyMatch(r -> r.getName().equals("ROLE_ADMIN")) || task.getAuthor().getId().equals(user.getId())) {

            task.setTitle(taskDto.getTitle());
            task.setStatus(Status.valueOf(taskDto.getStatus()));
            task.setPriority(Priority.valueOf(taskDto.getPriority()));
            taskRepository.save(task);
            return ResponseEntity.ok("Task was updated");
        } else {
            throw new AccessDeniedException("update", "task");
        }
    }


    public ResponseEntity<?> deleteById(Long id, String authorName) {
        User user = userService.findByUsername(authorName);
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task", "id", id));
        if (user.getRoles().stream().anyMatch(r -> r.getName().equals("ROLE_ADMIN")) || task.getAuthor().getId().equals(user.getId())) {
            taskRepository.deleteById(id);
            return ResponseEntity.ok("Task was deleted");
        } else {
            throw new AccessDeniedException("delete", "task");
        }
    }

    public Task setExecutors(Long id, List<String> executorsNames) {
        List<User> executors = new ArrayList<>();
        User executor = null;
        for (String executorName : executorsNames) {
            executor = userService.findByUsername(executorName);
            executors.add(executor);
        }

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task", "id", id));
        task.setExecutors(executors);
        return taskRepository.save(task);
    }

    public Task setStatus(Long id, String status) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task", "id", id));
        task.setStatus(Status.valueOf(status));
        return taskRepository.save(task);
    }

    public Task setPriority(Long id, String priority) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task", "id", id));
        task.setPriority(Priority.valueOf(priority));
        return taskRepository.save(task);
    }

    public List<Comment> getAllCommentsForTaskById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task", "id", id));
        return task.getComments().stream().toList();
    }

    public Comment createCommentForTaskById(Long id, String content, String authorName) {
        User author = userService.findByUsername(authorName);
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task", "id", id));

        Comment comment = new Comment();
        comment.setAuthor(author);
        comment.setContent(content);
        commentRepository.save(comment);

        List<Comment> commentsFromDB = task.getComments().stream().toList();
        List<Comment> comments = new ArrayList<>(commentsFromDB);
        comments.add(comment);

        task.setComments(comments);
        taskRepository.save(task);

        return comment;
    }

    public ResponseEntity<?> updateCommentForTaskById(Long id, Long commentId, String content, String authorName) {
        User user = userService.findByUsername(authorName);
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task", "id", id));

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

        if (user.getRoles().stream().anyMatch(r -> r.getName().equals("ROLE_ADMIN")) || comment.getAuthor().getUsername().equals(authorName)) {

            List<Comment> commentsFromDB = task.getComments().stream().toList();
            List<Comment> comments = new ArrayList<>(commentsFromDB);
            comments.remove(comment);
            comment.setContent(content);
            comments.add(comment);
            task.setComments(comments);

            taskRepository.save(task);
            commentRepository.save(comment);

            return new ResponseEntity<>("Comment was updated", HttpStatus.CREATED);
        } else {
            throw new AccessDeniedException("update", "comment");
        }
    }

    public ResponseEntity<?> deleteCommentForTaskById(Long id, Long commentId, String authorName) {
        User user = userService.findByUsername(authorName);
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task", "id", id));

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

        if (user.getRoles().stream().anyMatch(r -> r.getName().equals("ROLE_ADMIN")) || comment.getAuthor().getUsername().equals(authorName)) {

            List<Comment> commentsFromDB = task.getComments().stream().toList();
            List<Comment> comments = new ArrayList<>(commentsFromDB);
            comments.remove(comment);
            task.setComments(comments);
            commentRepository.delete(comment);
            taskRepository.save(task);

            return ResponseEntity.ok("Comment was deleted");
        } else {
            throw new AccessDeniedException("delete", "comment");
        }
    }

    public Task findById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task", "id", id));
    }
}
