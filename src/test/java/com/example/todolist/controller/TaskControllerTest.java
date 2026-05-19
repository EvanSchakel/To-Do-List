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

@SpringBootTest
@AutoConfigureMockMvc
public class TaskControllerTest {

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
    void testCreateTask() throws Exception {
        Task task = new Task("Test Task", "Test Description");

        mockMvc.perform(post("/api/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(task)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Test Task"))
                .andExpect(jsonPath("$.description").value("Test Description"))
                .andExpect(jsonPath("$.completed").value(false));
    }

    @Test
    void testCreateTaskWithEmptyTitle_ReturnsBadRequest() throws Exception {
        Task task = new Task("", "Test Description");

        mockMvc.perform(post("/api/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(task)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title").value("Title is required"));
    }

    @Test
    void testCreateTaskWithId_IgnoresIdAndCreatesNew() throws Exception {
        // First, create an existing task
        Task existingTask = new Task("Existing Task", "Existing Description");
        existingTask = taskRepository.save(existingTask);
        Long existingId = existingTask.getId();

        // Attempt to create a new task with the ID of the existing task
        Task newTaskWithId = new Task("New Task", "New Description");
        newTaskWithId.setId(existingId);

        mockMvc.perform(post("/api/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newTaskWithId)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(org.hamcrest.Matchers.not(existingId)))
                .andExpect(jsonPath("$.title").value("New Task"));

        // Verify the existing task was not overwritten
        mockMvc.perform(get("/api/tasks/" + existingId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Existing Task"));
    }

    @Test
    void testGetAllTasks() throws Exception {
        Task task1 = new Task("Task 1", "Desc 1");
        Task task2 = new Task("Task 2", "Desc 2");
        taskRepository.save(task1);
        taskRepository.save(task2);

        mockMvc.perform(get("/api/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].title").value("Task 1"))
                .andExpect(jsonPath("$[1].title").value("Task 2"));
    }

    @Test
    void testGetTaskById() throws Exception {
        Task task = new Task("Test Task", "Test Description");
        task = taskRepository.save(task);

        mockMvc.perform(get("/api/tasks/" + task.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Task"));
    }

    @Test
    void testUpdateTask() throws Exception {
        Task task = new Task("Original Title", "Original Desc");
        task = taskRepository.save(task);

        task.setTitle("Updated Title");
        task.setCompleted(true);

        mockMvc.perform(put("/api/tasks/" + task.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(task)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Title"))
                .andExpect(jsonPath("$.completed").value(true));
    }

    @Test
    void testUpdateTaskWithEmptyTitle_ReturnsBadRequest() throws Exception {
        Task task = new Task("Original Title", "Original Desc");
        task = taskRepository.save(task);

        task.setTitle("");

        mockMvc.perform(put("/api/tasks/" + task.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(task)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title").value("Title is required"));
    }

    @Test
    void testDeleteTask() throws Exception {
        Task task = new Task("Test Task", "Test Description");
        task = taskRepository.save(task);

        mockMvc.perform(delete("/api/tasks/" + task.getId()))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/tasks/" + task.getId()))
                .andExpect(status().isNotFound());
    }
}
