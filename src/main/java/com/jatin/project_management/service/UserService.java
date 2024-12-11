package com.jatin.project_management.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.jatin.project_management.dto.UserRegistrationDto;
import com.jatin.project_management.entity.User;
import com.jatin.project_management.entity.UserRole;
import com.jatin.project_management.exception.ResourceNotFoundException;
import com.jatin.project_management.exception.UnauthorizedAccessException;
import com.jatin.project_management.repository.UserRepository;

/**
 * UserService
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User not found with id " + id));
    }

    public User getUserByUsername(String userName) {
        return userRepository.findByUsername(userName).orElseThrow(
                () -> new ResourceNotFoundException("User not found with userName " + userName));
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    /**
     * Get current user
     * @return
     */
    public User getCurrentUser() {
        Long currentUserId = JwtService.getCurrentUserPrincipal().getUserId();
        return getUserById(currentUserId);
    }

    /**
     * Updates current user
     * 
     * @param userDto
     * @return
     */
    public User updateUser(UserRegistrationDto userDto) {
        Long currentUserId = JwtService.getCurrentUserPrincipal().getUserId();
        return updateUser(currentUserId, userDto);
    }

    /**
     * Deletes current user
     * 
     * @param userDto
     * @return
     */
    public void deleteUser() {
        Long currentUserId = JwtService.getCurrentUserPrincipal().getUserId();
        deleteUser(currentUserId);
    }

    public User updateUser(Long id, UserRegistrationDto userDto) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User does not exist"));
        user.setPassword(userDto.getPassword());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User does not exist"));
        Long currentUserId = JwtService.getCurrentUserPrincipal().getUserId();
        if (!user.getId().equals(currentUserId)) {
            throw new UnauthorizedAccessException("Not authorized to delete/modify other account");
        }
        userRepository.deleteById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public User makeUserAdmin(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User does not exist"));
        user.setRole(UserRole.ADMIN);
        return userRepository.save(user);
    }

}
