package com.example.todolist.controller;

import com.example.todolist.model.Task;
import com.example.todolist.repository.TaskRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
public class TaskControllerSecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCreatedAtMassAssignment() throws Exception {
        Task task = new Task("Hacked Task", "Description");
        LocalDateTime pastDate = LocalDateTime.of(1990, 1, 1, 0, 0);
        task.setCreatedAt(pastDate);

        MvcResult result = mockMvc.perform(post("/api/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(task)))
                .andExpect(status().isCreated())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        Task createdTask = objectMapper.readValue(responseBody, Task.class);

        System.out.println("Created At in response: " + createdTask.getCreatedAt());

        Task dbTask = taskRepository.findById(createdTask.getId()).orElseThrow();
        System.out.println("Created At in DB: " + dbTask.getCreatedAt());

        org.junit.jupiter.api.Assertions.assertNotEquals(pastDate, dbTask.getCreatedAt(), "Mass assignment vulnerability on createdAt is still present!");
        org.junit.jupiter.api.Assertions.assertTrue(dbTask.getCreatedAt().isAfter(pastDate), "The createdAt date should be overwritten with the current time.");
    }
}
