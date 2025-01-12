package com.taskmanager.demo.config;
import com.taskmanager.demo.entity.Comment;
import com.taskmanager.demo.entity.Role;
import com.taskmanager.demo.entity.Task;
import com.taskmanager.demo.entity.User;
import com.taskmanager.demo.entity.enums.Priority;
import com.taskmanager.demo.entity.enums.Status;
import com.taskmanager.demo.repository.CommentRepository;
import com.taskmanager.demo.repository.RoleRepository;
import com.taskmanager.demo.repository.TaskRepository;
import com.taskmanager.demo.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
@AllArgsConstructor
public class DataLoader {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final CommentRepository commentRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    private void loadData(){
        roleRepository.deleteAll();
        userRepository.deleteAll();
        taskRepository.deleteAll();
        commentRepository.deleteAll();
        String password = passwordEncoder.encode("100");

        Role role1 = new Role();
        role1.setName("ROLE_ADMIN");

        Role role2 = new Role();
        role2.setName("ROLE_USER");

        roleRepository.save(role1);
        roleRepository.save(role2);

        User user1 = new User();
        user1.setUsername("Alex");
        user1.setPassword(password);
        user1.setRoles(List.of(role1));
        user1.setEmail("alex@gmail");

        User user2 = new User();
        user2.setUsername("Bob");
        user2.setPassword(password);
        user2.setRoles(List.of(role2));
        user2.setEmail("bob@gmail");

        User user3 = new User();
        user3.setUsername("Aleksei");
        user3.setPassword(password);
        user3.setRoles(List.of(role2));
        user3.setEmail("aleksei@gmail");

        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);

        Comment comment1 = new Comment();
        comment1.setAuthor(user2);
        comment1.setContent("Задача слишком сложна");


        Comment comment2 = new Comment();
        comment2.setAuthor(user3);
        comment2.setContent("Интересная задача");

        Comment comment3 = new Comment();
        comment3.setAuthor(user2);
        comment3.setContent("Решение потребует больше времени");

        commentRepository.save(comment1);
        commentRepository.save(comment2);
        commentRepository.save(comment3);

        Task task1 = new Task();
        task1.setAuthor(user1);
        task1.setExecutors(List.of(user2, user3));
        task1.setComments(List.of(comment1, comment3));
        task1.setStatus(Status.IN_PROCESS);
        task1.setPriority(Priority.HIGH);
        task1.setCreationDate(new Date());
        task1.setTitle("Создание программного продукта");

        Task task2 = new Task();
        task2.setAuthor(user2);
        task2.setExecutors(List.of(user3));
        task2.setComments(List.of(comment2));
        task2.setStatus(Status.WAITING);
        task2.setPriority(Priority.LOW);
        task2.setCreationDate(new Date());
        task2.setTitle("Реализовать базу данных");


        taskRepository.save(task1);
        taskRepository.save(task2);

    }
}
