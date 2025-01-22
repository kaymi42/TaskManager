package com.taskmanager.demo.controller;

import com.taskmanager.demo.dto.TaskDto;
import com.taskmanager.demo.entity.Task;
import com.taskmanager.demo.entity.User;
import com.taskmanager.demo.entity.enums.Priority;
import com.taskmanager.demo.entity.enums.Status;
import com.taskmanager.demo.service.impl.TaskServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.security.Principal;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class TaskControllerTest {

    @Mock
    TaskServiceImpl taskService;

    @InjectMocks
    TaskController taskController;

    @Test
    void createTask_RequestIsValid_ReturnsNewTask() throws Exception{
        // given
        var taskDto = new TaskDto();
        taskDto.setTitle("Another valid task");
        taskDto.setStatus(Status.IN_PROCESS.name());
        taskDto.setPriority(Priority.LOW.name());

        var currentUser = new Principal() {
            @Override
            public String getName() {
                return "Alex";
            }
        };

        doReturn(new Task(3L,
                Status.IN_PROCESS,
                Priority.LOW,
                new Date(),
                "Another valid task",
                new User("Alex"),
                null,
                null)).when(this.taskService).createTask(taskDto, currentUser.getName());

        // when
        var result = taskController.createTask(taskDto, currentUser);

        // then
        assertNotNull(result);
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(taskDto.getTitle(), result.getBody().getTitle());
        assertEquals(taskDto.getStatus(), result.getBody().getStatus().name());
        assertEquals(taskDto.getPriority(), result.getBody().getPriority().name());
    }
}