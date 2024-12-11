package com.jatin.project_management.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.jatin.project_management.dto.TaskDto;
import com.jatin.project_management.entity.Project;
import com.jatin.project_management.entity.Task;
import com.jatin.project_management.exception.ResourceNotFoundException;
import com.jatin.project_management.repository.ProjectRepository;
import com.jatin.project_management.repository.TaskRepository;

import jakarta.persistence.criteria.Predicate;

/**
 * TaskService
 */
@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ProjectRepository projectRepository;

    /**
     * @param taskDto
     * @return
     */
    public Task createTask(TaskDto taskDto) {
        Project project = projectRepository.findById(taskDto.getProjectId())
                .orElseThrow(() -> new RuntimeException("Project not found with id: " + taskDto.getProjectId()));

        Task task = Task.builder()
                .name(taskDto.getName())
                .description(taskDto.getDescription())
                .project(project)
                .dueDate(taskDto.getDueDate())
                .build();

        return taskRepository.save(task);
    }

    /**
     * Search task and filter based on name, description and projectId
     * and sortBy
     * 
     * @param name
     * @param description
     * @param projectId
     * @param sortBy
     * @param sortDirection
     * @return
     */
    public List<Task> searchTasks(String name, String description, Long projectId,
            String sortBy, String sortDirection) {
        Specification<Task> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (name != null && !name.isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
            }
            if (description != null && !description.isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("description")), "%" + description.toLowerCase() + "%"));
            }
            if (projectId != null) {
                predicates.add(cb.equal(root.get("project").get("id"), projectId));
            }
            query.orderBy(
                    sortDirection.equalsIgnoreCase("desc")
                            ? cb.desc(root.get(sortBy))
                            : cb.asc(root.get(sortBy)));

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        return taskRepository.findAll(spec);
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Task getTaskById(Long taskId) {
        return taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not exists with id" + taskId));
    }

    public Task updateTask(Long taskId, TaskDto updatedTask) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not exists with id" + taskId));
        task.setName(updatedTask.getName());
        task.setDueDate(updatedTask.getDueDate());
        task.setDescription(updatedTask.getDescription());
        return taskRepository.save(task);
    }

    public void deleteTask(Long taskId) {
        taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not exists with id" + taskId));
        taskRepository.deleteById(taskId);
    }
}
