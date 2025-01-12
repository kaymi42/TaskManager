package com.taskmanager.demo.controller;

import com.taskmanager.demo.entity.Comment;
import com.taskmanager.demo.entity.Task;
import com.taskmanager.demo.entity.User;
import com.taskmanager.demo.service.impl.UserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {
    private UserServiceImpl userService;

    @RequestMapping(value = "/me", method = RequestMethod.GET)
    public ResponseEntity<?> userInfo(Principal currentUser){
        User user = userService.findByUsername(currentUser.getName());
        return ResponseEntity.ok(user);
    }

    @RequestMapping(value = "/me/tasks", method = RequestMethod.GET)
    public ResponseEntity<?> getAllTasksByUserId(Principal currentUser){
        User user = userService.findByUsername(currentUser.getName());
        List<Task> tasks = userService.getAllTasksByAuthorId(user.getId());
        return ResponseEntity.ok(tasks);
    }

    @RequestMapping(value = "/me/comments", method = RequestMethod.GET)
    public ResponseEntity<?> getAllCommentsByUserId(Principal currentUser){
        User user = userService.findByUsername(currentUser.getName());
        List<Comment> comments = userService.getAllCommentsByAuthorId(user.getId());
        return ResponseEntity.ok(comments);
    }

    @RequestMapping(value = "/{username}/info", method = RequestMethod.GET)
    public ResponseEntity<?> userInfoByUsername(@PathVariable("username") String username){
        User user = userService.findByUsername(username);
        return ResponseEntity.ok(user);
    }

    @RequestMapping(value = "/{username}/tasks", method = RequestMethod.GET)
    public ResponseEntity<?> getAllTasksByUsername(@PathVariable("username") String username){
        User user = userService.findByUsername(username);
        List<Task> tasks = userService.getAllTasksByAuthorId(user.getId());
        return ResponseEntity.ok(tasks);
    }
    // TODO:321321
    @RequestMapping(value = "/{username}/comments", method = RequestMethod.GET)
    public ResponseEntity<?> getAllCommentsByUsername(@PathVariable("username") String username){
        User user = userService.findByUsername(username);
        List<Comment> comments = userService.getAllCommentsByAuthorId(user.getId());
        return ResponseEntity.ok(comments);
    }

}
