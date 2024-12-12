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

import com.jatin.project_management.dto.ProjectDto;
import com.jatin.project_management.entity.Project;
import com.jatin.project_management.service.ProjectService;

/**
 * ProjectController
 */
@RestController
@RequestMapping("/api/projects")
public class ProjectController {

	@Autowired
	private ProjectService projectService;

	@GetMapping
	public ResponseEntity<List<Project>> getProjects(
			@RequestParam(required = false) String name,
			@RequestParam(required = false) String description,
			@RequestParam(defaultValue = "createdAt") String sortBy,
			@RequestParam(defaultValue = "asc") String sortDirection) {

		return ResponseEntity.ok(projectService.searchProjects(name, description, sortBy, sortDirection));
	}

	@GetMapping("/{projectId}")
	public ResponseEntity<Project> getProject(@PathVariable Long projectId) {
		return ResponseEntity.ok(projectService.getProjectById(projectId));
	}

	@PostMapping
	public ResponseEntity<Project> createProject(@RequestBody ProjectDto projectDto) {
		return ResponseEntity.ok(projectService.createProject(projectDto));
	}

	@PutMapping("/{projectId}")
	public ResponseEntity<Project> updateProject(@PathVariable Long projectId, @RequestBody ProjectDto updatedProject) {
		return ResponseEntity.ok(projectService.updateProject(projectId, updatedProject));
	}

	@DeleteMapping("/{projectId}")
	public ResponseEntity<Void> updateProject(@PathVariable Long projectId) {
	projectService.deleteProject(projectId);
		return ResponseEntity.noContent().build();
	}
}
