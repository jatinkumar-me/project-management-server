package com.jatin.project_management.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.jatin.project_management.entity.Task;

import lombok.Data;

/**
 * ProjectDto
 */
@Data
public class ProjectDto {

	private String name;
	private String description;
	private List<Task> tasks;
	private LocalDateTime dueDate = LocalDateTime.MAX;
}
