package com.jatin.project_management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.jatin.project_management.dto.AuthResponseDto;
import com.jatin.project_management.dto.UserLoginDto;
import com.jatin.project_management.dto.UserRegistrationDto;
import com.jatin.project_management.entity.User;
import com.jatin.project_management.entity.UserRole;

/**
 * AuthService
 */
@Service
public class AuthService {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    /**
     * Register a new user by encoding their password and saving them in the
     * database.
     *
     * @param userRegistrationDto The user to register.
     * @return The registered user.
     */
    public User register(UserRegistrationDto userRegistrationDto) {

        User newUser = User.builder()
                .firstName(userRegistrationDto.getFirstName())
                .lastName(userRegistrationDto.getLastName())
                .username(userRegistrationDto.getUsername())
                .role(UserRole.USER)
                .build();

        newUser.setPassword(encoder.encode(userRegistrationDto.getPassword()));
        userService.createUser(newUser);
        return newUser;
    }

    /**
     * Verify a user's credentials, generate a JWT token, and return authentication
     * response.
     *
     * @param userLoginDto The user's authentication request containing
     *                     email and password.
     * @return An AuthenticationResponse containing the JWT token and user details.
     */
    public AuthResponseDto verify(UserLoginDto userLoginDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userLoginDto.getUsername(),
                        userLoginDto.getPassword()));

        User user = userService.getUserByUsername(userLoginDto.getUsername());
        if (user == null) {
            return null;
        }

        String token = jwtService.generateToken(user.getUsername(), user.getRole());
        AuthResponseDto response = AuthResponseDto.builder()
                .token(token)
                .user(user)
                .build();
        return response;
    }

}
