package com.jatin.project_management.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.jatin.project_management.dto.ProjectDto;
import com.jatin.project_management.entity.Project;
import com.jatin.project_management.exception.ResourceNotFoundException;
import com.jatin.project_management.repository.ProjectRepository;

import jakarta.persistence.criteria.Predicate;

/**
 * ProjectService
 */
@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    /**
     * @param projectDto
     * @return
     */
    public Project createProject(ProjectDto projectDto) {
        Project project = Project.builder()
                .name(projectDto.getName())
                .description(projectDto.getDescription())
                .tasks(projectDto.getTasks())
                .dueDate(projectDto.getDueDate())
                .build();

        return projectRepository.save(project);
    }

    /**
     * Search project and filter based on name, description and projectId
     * and sortBy
     * 
     * @param name
     * @param description
     * @param projectId
     * @param sortBy
     * @param sortDirection
     * @return
     */
    public List<Project> searchProjects(String name, String description, String sortBy, String sortDirection) {
        Specification<Project> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (name != null && !name.isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
            }
            if (description != null && !description.isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("description")), "%" + description.toLowerCase() + "%"));
            }
            query.orderBy(
                    sortDirection.equalsIgnoreCase("desc")
                            ? cb.desc(root.get(sortBy))
                            : cb.asc(root.get(sortBy)));

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        return projectRepository.findAll(spec);
    }

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public Project getProjectById(Long projectId) {
        return projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not exists with id" + projectId));
    }

    public Project updateProject(Long projectId, ProjectDto updatedProject) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not exists with id" + projectId));
        project.setName(updatedProject.getName());
        project.setDueDate(updatedProject.getDueDate());
        project.setDescription(updatedProject.getDescription());
        return projectRepository.save(project);
    }

    public void deleteProject(Long projectId) {
        projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not exists with id" + projectId));
        projectRepository.deleteById(projectId);
    }
}
