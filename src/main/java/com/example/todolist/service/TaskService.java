package com.example.todolist.service;

import com.example.todolist.model.Task;
import com.example.todolist.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    // ⚡ Bolt Performance Optimization:
    // Added @Transactional(readOnly = true) to disable Hibernate's dirty checking mechanism.
    // Impact: Improves read performance by avoiding unnecessary snapshot creation and memory overhead during read operations.
    @Transactional(readOnly = true)
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    // ⚡ Bolt Performance Optimization:
    // Added @Transactional(readOnly = true) to disable Hibernate's dirty checking mechanism.
    // Impact: Improves read performance by avoiding unnecessary snapshot creation and memory overhead during read operations.
    @Transactional(readOnly = true)
    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }

    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    // ⚡ Bolt Performance Optimization:
    // Added @Transactional to keep the entity managed in the persistence context during the update operation.
    // Impact: Eliminates a redundant SELECT query before the UPDATE execution by avoiding entity detachment.
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
