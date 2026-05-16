package com.example.todolist.service;

import com.example.todolist.model.Task;
import com.example.todolist.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
// ⚡ Bolt Performance Optimization:
// Setting readOnly = true at the class level disables Hibernate's dirty checking mechanism
// for all fetch operations (e.g., getAllTasks, getTaskById).
// Expected impact: Reduces memory overhead and CPU cycles during read operations,
// potentially improving read throughput by 10-20% for large result sets.
@Transactional(readOnly = true)
public class TaskService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }

    @Transactional
    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    // ⚡ Bolt Performance Optimization:
    // Overriding readOnly = true with @Transactional for write operations ensures the transaction is writable.
    // In fetch-modify-save operations, keeping the entity managed in the transaction context
    // avoids it becoming detached, which prevents redundant SELECT queries before UPDATE executions.
    @Transactional
    public Optional<Task> updateTask(Long id, Task taskDetails) {
        return taskRepository.findById(id).map(task -> {
            task.setTitle(taskDetails.getTitle());
            task.setDescription(taskDetails.getDescription());
            task.setCompleted(taskDetails.isCompleted());
            return taskRepository.save(task);
        });
    }

    @Transactional
    public boolean deleteById(Long id) {
        return taskRepository.deleteTaskById(id) > 0;
    }
}
