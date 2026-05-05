package com.example.todolist.service;

import com.example.todolist.dto.TaskRequest;
import com.example.todolist.dto.TaskResponse;
import com.example.todolist.exception.TaskNotFoundException;
import com.example.todolist.model.Task;
import com.example.todolist.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllTasks() {
        Task task1 = new Task("Task 1", "Desc 1");
        task1.setId(1L);
        Task task2 = new Task("Task 2", "Desc 2");
        task2.setId(2L);

        when(taskRepository.findAll()).thenReturn(Arrays.asList(task1, task2));

        List<TaskResponse> tasks = taskService.getAllTasks();

        assertEquals(2, tasks.size());
        assertEquals("Task 1", tasks.get(0).title());
        assertEquals("Task 2", tasks.get(1).title());
    }

    @Test
    void testGetTaskById() {
        Task task = new Task("Test Task", "Desc");
        task.setId(1L);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        TaskResponse response = taskService.getTaskById(1L);

        assertEquals("Test Task", response.title());
    }

    @Test
    void testGetTaskByIdNotFound() {
        when(taskRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> taskService.getTaskById(1L));
    }

    @Test
    void testCreateTask() {
        TaskRequest request = new TaskRequest("New Task", "New Desc", false);
        Task savedTask = new Task("New Task", "New Desc");
        savedTask.setId(1L);

        when(taskRepository.save(any(Task.class))).thenReturn(savedTask);

        TaskResponse response = taskService.createTask(request);

        assertEquals("New Task", response.title());
        assertNotNull(response.id());
    }

    @Test
    void testUpdateTask() {
        Task existingTask = new Task("Old Title", "Old Desc");
        existingTask.setId(1L);

        TaskRequest request = new TaskRequest("New Title", "New Desc", true);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(existingTask));
        when(taskRepository.save(any(Task.class))).thenAnswer(invocation -> invocation.getArgument(0));

        TaskResponse response = taskService.updateTask(1L, request);

        assertEquals("New Title", response.title());
        assertTrue(response.completed());
    }

    @Test
    void testDeleteTask() {
        when(taskRepository.existsById(1L)).thenReturn(true);

        taskService.deleteTask(1L);

        verify(taskRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteTaskNotFound() {
        when(taskRepository.existsById(1L)).thenReturn(false);

        assertThrows(TaskNotFoundException.class, () -> taskService.deleteTask(1L));
    }
}
