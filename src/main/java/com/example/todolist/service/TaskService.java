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
// Setting @Transactional(readOnly = true) at the class level disables Hibernate's
// dirty checking mechanism for read operations, significantly improving read performance
// and reducing memory overhead. Write methods explicitly override this.
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
        // Prevent Mass Assignment/IDOR: ensure a new record is created rather than updating an existing one
        task.setId(null);
        // Prevent Mass Assignment on audit fields: explicitly set createdAt to now
        task.setCreatedAt(java.time.LocalDateTime.now());
        return taskRepository.save(task);
    }

    @Transactional
    public Optional<Task> updateTask(Long id, Task taskDetails) {
        return taskRepository.findById(id).map(task -> {
            task.setTitle(taskDetails.getTitle());
            task.setDescription(taskDetails.getDescription());
            task.setCompleted(taskDetails.isCompleted());

            // ⚡ Bolt Performance Optimization:
            // Removed redundant taskRepository.save(task) call.
            // Since this method runs within a @Transactional context and the entity
            // is attached, JPA's automatic dirty checking will detect the changes
            // and flush an UPDATE statement to the database at transaction commit.
            // This avoids an unnecessary em.merge() which would trigger a redundant SELECT query.
            return task;
        });
    }

    @Transactional
    public boolean deleteById(Long id) {
        return taskRepository.deleteTaskById(id) > 0;
    }
}
