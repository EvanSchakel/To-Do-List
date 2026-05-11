package com.example.todolist.controller;

import com.example.todolist.model.Task;
import com.example.todolist.repository.TaskRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
public class TaskControllerSecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        taskRepository.deleteAll();
    }

    @Test
    void testOverpostingIdOnCreate() throws Exception {
        Task originalTask = new Task("Original", "Original");
        originalTask = taskRepository.save(originalTask);

        Long originalId = originalTask.getId();

        Task hackedTask = new Task("Hacked", "Hacked");
        hackedTask.setId(originalId);

        mockMvc.perform(post("/api/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(hackedTask)))
                .andExpect(status().isCreated());

        Task checkTask = taskRepository.findById(originalId).orElseThrow();
        assertEquals("Original", checkTask.getTitle(), "Mass assignment vulnerability: Existing task was overwritten by POST");
    }
}
