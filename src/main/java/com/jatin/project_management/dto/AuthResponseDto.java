package com.jatin.project_management.dto;

import com.jatin.project_management.entity.User;

import lombok.Builder;
import lombok.Data;

/**
 * AuthResponseDto
 */
@Data
@Builder
public class AuthResponseDto {

    private User user;
    private String token;
}
