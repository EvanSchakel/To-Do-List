package com.example.todolist.service;

import com.example.todolist.model.Task;
import com.example.todolist.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    private Task task;

    @BeforeEach
    void setUp() {
        task = new Task("Test Task", "Description");
        task.setId(1L);
    }

    @Test
    void testGetAllTasks() {
        when(taskRepository.findAll()).thenReturn(Arrays.asList(task));
        List<Task> tasks = taskService.getAllTasks();
        assertEquals(1, tasks.size());
        assertEquals("Test Task", tasks.get(0).getTitle());
    }

    @Test
    void testGetTaskById() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        Optional<Task> foundTask = taskService.getTaskById(1L);
        assertTrue(foundTask.isPresent());
        assertEquals("Test Task", foundTask.get().getTitle());
    }

    @Test
    void testCreateTask() {
        Task inputTask = new Task("Test Task", "Description");
        inputTask.setId(999L); // Simulate mass assignment attempt

        when(taskRepository.save(any(Task.class))).thenAnswer(invocation -> {
            Task savedTask = invocation.getArgument(0);
            assertNull(savedTask.getId(), "Task ID should be nullified before saving to prevent mass assignment");
            savedTask.setId(1L); // Simulate DB generating an ID
            return savedTask;
        });

        Task createdTask = taskService.createTask(inputTask);
        assertNotNull(createdTask);
        assertEquals("Test Task", createdTask.getTitle());
        assertEquals(1L, createdTask.getId());
    }

    @Test
    void testUpdateTask() {
        Task updateDetails = new Task("Updated Task", "Updated Description");
        updateDetails.setCompleted(true);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenAnswer(i -> i.getArguments()[0]);

        Optional<Task> updatedTask = taskService.updateTask(1L, updateDetails);

        assertTrue(updatedTask.isPresent());
        assertEquals("Updated Task", updatedTask.get().getTitle());
        assertEquals("Updated Description", updatedTask.get().getDescription());
        assertTrue(updatedTask.get().isCompleted());
    }

    @Test
    void testUpdateTaskNotFound() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());
        Optional<Task> updatedTask = taskService.updateTask(1L, new Task());
        assertFalse(updatedTask.isPresent());
    }

    @Test
    void testDeleteTask() {
        when(taskRepository.deleteTaskById(1L)).thenReturn(1);
        boolean result = taskService.deleteById(1L);
        assertTrue(result);
        verify(taskRepository, times(1)).deleteTaskById(1L);
    }

    @Test
    void testDeleteTaskNotFound() {
        when(taskRepository.deleteTaskById(2L)).thenReturn(0);
        boolean result = taskService.deleteById(2L);
        assertFalse(result);
        verify(taskRepository, times(1)).deleteTaskById(2L);
    }
}
