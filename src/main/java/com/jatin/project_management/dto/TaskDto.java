package com.jatin.project_management.dto;

import java.time.LocalDateTime;

import lombok.Data;

/**
 * TaskDto
 */
@Data
public class TaskDto {

    private Long name;
    private Long description;
    private Long projectId;
    private LocalDateTime dueDate;
}
