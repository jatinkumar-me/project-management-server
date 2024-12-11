package com.jatin.project_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jatin.project_management.entity.Project;

/**
 * ProjectRepository
 */
public interface ProjectRepository extends JpaRepository<Project, Long> {
}
