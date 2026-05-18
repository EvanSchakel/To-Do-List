package com.example.todolist.service;

import com.example.todolist.model.Task;
import com.example.todolist.repository.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
// ⚡ Bolt Performance Optimization:
// Setting readOnly = true at the class level disables Hibernate's dirty checking
// for read operations (getAllTasks, getTaskById). This reduces memory usage and CPU
// cycles since Hibernate doesn't need to keep snapshots of entities or check for
// modifications before returning.
// Expected Impact: Faster read operations and lower memory footprint, especially
// under high concurrent load when fetching multiple tasks.
@Transactional(readOnly = true)
public class TaskService {

    private final TaskRepository taskRepository;

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
