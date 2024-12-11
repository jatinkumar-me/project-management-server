package com.jatin.project_management.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Data;

/**
 * Task
 */
@Entity
@Data
@Builder
public class Task {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long name;
	private Long description;
	
	@ManyToOne
	@JoinColumn(name = "project_id")
	private Project project;

    @Column(nullable = false, updatable = false)
	@Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = true, updatable = true)
    private LocalDateTime dueDate;
}
