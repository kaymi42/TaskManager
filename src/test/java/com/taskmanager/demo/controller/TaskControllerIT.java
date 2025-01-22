package com.taskmanager.demo.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
class TaskControllerIT {

    @Autowired
    MockMvc mockMvc;

    @Test
//    @Sql("/sql/tasks.sql")
    void getAllTasks_ReturnsListTasks() throws Exception {
        // given
        var requestBuilder = MockMvcRequestBuilders.get("/tasks")
                .with(user("Bob").roles("USER"));

        // when
        this.mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                        content().json("""
                                [
                                    {
                                    "id":1,
                                    "status":"IN_PROCESS",
                                    "priority":"HIGH",
                                    "title":"Create a new program product base on Spring"
                                    },
                                    {
                                    "id":2,
                                    "status":"WAITING",
                                    "priority":"LOW",
                                    "title":"Create documentation for all controllers"
                                    }
                                ]
                                """)
                );
    }

    @Test
    void createTask_RequestIsValid_ReturnsNewTask() throws Exception {
        // given
        var requestBuilder = MockMvcRequestBuilders.post("/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "title":"Another task",
                            "status":"WAITING",
                            "priority":"LOW"
                        }
                        """)
                .with(user("Bob").roles("USER"));

        // when
        this.mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpectAll(
                        status().isCreated(),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                        content().json("""
                                    {
                                    "id":3,
                                    "title":"Another task",
                                    "status":"WAITING",
                                    "priority":"LOW",
                                    "author": {"username":"Bob","roles":[{"name":"ROLE_USER"}]}
                                    }
                                """)
                );
    }

    @Test
    void createTask_RequestIsInvalid_ReturnsErrorDetail() throws Exception {
        // given
        var requestBuilder = MockMvcRequestBuilders.post("/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "title":"Invalid",
                            "status":"in process",
                            "priority":"LOW"
                        }
                        """)
                .with(user("Bob").roles("USER"));

        // when
        this.mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpectAll(
                        status().isBadRequest(),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                        content().json("""
                                    {
                                    "status":400,
                                    "message":[
                                    "title - must match \\"[\\\\w\\\\s\\\\p{Punct}]{10,100}\\"",
                                    "title - size must be between 10 and 100",
                                    "status - must match \\"WAITING|IN_PROCESS|COMPLETED\\""]
                                    }
                                """)
                );
    }

    @Test
    void createTask_UserIsNotAuthorized_ReturnsUnauthorized() throws Exception {
        // given
        var requestBuilder = MockMvcRequestBuilders.post("/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "title":"Another task",
                            "status":"WAITING",
                            "priority":"LOW"
                        }
                        """);

        // when
        this.mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpectAll(
                        status().isUnauthorized()
                );
    }
}