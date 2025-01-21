package com.taskmanager.demo.controller;

import com.taskmanager.demo.dto.*;
import com.taskmanager.demo.entity.Comment;
import com.taskmanager.demo.entity.Task;
import com.taskmanager.demo.service.impl.TaskServiceImpl;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/tasks")
public class TaskController {

    private final TaskServiceImpl taskService;

    // === ALL ===
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getAllTasks(){
        List<Task> allTasks = taskService.getAllTasks();
        return ResponseEntity.ok(allTasks);
    }

    // === ALL ===
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> createTask(@Valid @RequestBody TaskDto taskDto, Principal principal){
        Task task = taskService.createTask(taskDto, principal.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(task);
    }

    // === ALL ===
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getTaskById(@PathVariable("id") Long id){
        Task task = taskService.findById(id);
        return ResponseEntity.ok(task);
    }

    // === ADMIN or AUTHOR ===
    @RequestMapping(value = "/{id}", method = RequestMethod.PATCH)
    public ResponseEntity<?> updateTaskById(@PathVariable("id") Long id,@Valid @RequestBody TaskDto taskDto, Principal principal){
        return taskService.updateTaskById(id, taskDto, principal.getName());

    }

    // === ADMIN or AUTHOR ===
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteTaskById(@PathVariable("id") Long id, Principal principal){
        return taskService.deleteById(id, principal.getName());
    }

    // === ADMIN ===
    @RequestMapping(value = "/{id}/executors", method = RequestMethod.PATCH)
    public ResponseEntity<?> setExecutorsForTask(@PathVariable("id") Long id, @Valid @RequestBody Executors executorsNames){
        Task task = taskService.setExecutors(id, executorsNames.getExecutorsNames());
        return ResponseEntity.ok(task);
    }

    // === ALL ===
    @RequestMapping(value = "/{id}/status", method = RequestMethod.PATCH)
    public ResponseEntity<?> setStatusForTask(@PathVariable("id") Long id, @Valid @RequestBody StatusDto status){
        Task task = taskService.setStatus(id,status.getStatus());
        return ResponseEntity.ok(task);
    }

    // === ADMIN ===
    @RequestMapping(value = "/{id}/priority", method = RequestMethod.PATCH)
    public ResponseEntity<?> setPriorityForTask(@PathVariable("id") Long id, @Valid @RequestBody PriorityDto priority){
        Task task = taskService.setPriority(id, priority.getPriority());
        return ResponseEntity.ok(task);
    }

    // === ALL ===
    @RequestMapping(value = "/{id}/comments", method = RequestMethod.GET)
    public ResponseEntity<?> getAllCommentsForTaskById(@PathVariable("id") Long id){
        List<Comment> commentsList = taskService.getAllCommentsForTaskById(id);
        return ResponseEntity.ok(commentsList);
    }

    // === ADMIN or AUTHOR or EXECUTORS ===
    @RequestMapping(value = "/{id}/comments", method = RequestMethod.POST)
    public ResponseEntity<?> createCommentForTaskById(
            @PathVariable("id") Long id,
            @Valid @RequestBody CommentContentDto content,
            Principal principal)
    {
        Comment comment = taskService.createCommentForTaskById(id, content.getContent(), principal.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(comment);
    }

    // === AUTHOR OF COMMENT or ADMIN ===
    @RequestMapping(value = "/{id}/comments/{commentId}", method = RequestMethod.PATCH)
    public ResponseEntity<?> updateCommentForTaskById(
            @PathVariable("id") Long id,
            @PathVariable("commentId") Long commentId,
            @Valid @RequestBody CommentContentDto content,
            Principal principal)
    {
        return taskService.updateCommentForTaskById(id, commentId, content.getContent(), principal.getName());
    }

    // === AUTHOR OF COMMENT or ADMIN ===
    @RequestMapping(value = "/{id}/comments/{commentId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteCommentForTaskById(
            @PathVariable("id") Long id,
            @PathVariable("commentId") Long commentId,
            Principal principal)
    {
        return taskService.deleteCommentForTaskById(id, commentId, principal.getName());
    }
}
