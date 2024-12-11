package com.jatin.project_management.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jatin.project_management.dto.TaskDto;
import com.jatin.project_management.entity.Task;
import com.jatin.project_management.service.TaskService;

/**
 * TaskController
 */
@RestController
@RequestMapping("/api/tasks")
public class TaskController {

	@Autowired
	private TaskService taskService;

	@GetMapping
	public ResponseEntity<List<Task>> getTasks(
			@RequestParam(required = false) String name,
			@RequestParam(required = false) String description,
			@RequestParam(required = false) Long projectId,
			@RequestParam(defaultValue = "createdAt") String sortBy,
			@RequestParam(defaultValue = "asc") String sortDirection) {

		return ResponseEntity.ok(taskService.searchTasks(name, description, projectId, sortBy, sortDirection));
	}

	@PostMapping
	public ResponseEntity<Task> createTask(@RequestBody TaskDto taskDto) {
		return ResponseEntity.ok(taskService.createTask(taskDto));
	}

	@PutMapping("/{taskId}")
	public ResponseEntity<Task> updateTask(@PathVariable Long taskId, @RequestBody TaskDto updatedTask) {
		return ResponseEntity.ok(taskService.updateTask(taskId, updatedTask));
	}

	@DeleteMapping("/{taskId}")
	public ResponseEntity<Void> updateTask(@PathVariable Long taskId) {
	taskService.deleteTask(taskId);
		return ResponseEntity.noContent().build();
	}
}
