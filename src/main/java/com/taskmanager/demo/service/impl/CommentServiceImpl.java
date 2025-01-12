package com.taskmanager.demo.service.impl;

import com.taskmanager.demo.entity.Comment;
import com.taskmanager.demo.entity.Task;
import com.taskmanager.demo.entity.User;
import com.taskmanager.demo.repository.CommentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CommentServiceImpl {
    private final CommentRepository commentRepository;
    private final TaskServiceImpl taskService;
    private final UserServiceImpl userService;


//    public List<Comment> getAllCommentsForTaskById(Long id) {
//        List<Comment> list = commentRepository.findByTaskId(id).orElseThrow();
//        List<Comment> resultList = new ArrayList<>();
//        for(Comment c : list){
//            Comment comment = new Comment();
//            comment.setAuthor(new User(c.getAuthor().getUsername()));
//            comment.setContent(c.getContent());
//            resultList.add(comment);
//        }
//        return resultList;
//    }
//
//    public Comment createCommentForTaskById(Long id, String content, String authorName) {
//        User author = userService.findByUsername(authorName).orElseThrow();
//        Task task = taskService.getTaskById(id);
//
//        Comment comment = new Comment();
//        comment.setContent(content);
//        comment.setAuthor(author);
//        comment.setTask(task);
//
//        commentRepository.save(comment);
//
//        Comment result = new Comment();
//        result.setContent(comment.getContent());
//        result.setAuthor(new User(comment.getAuthor().getUsername()));
//        return result;
//    }
}
