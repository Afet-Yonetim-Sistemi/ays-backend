package com.ays.backend.user.service;

import com.ays.backend.user.controller.payload.request.SignUpRequest;
import com.ays.backend.user.service.dto.UserDTO;

/**
 * User service to perform user related business operations.
 */
public interface UserService {
    /**
     * Saves a signUpRequest to the database.
     *
     * @param signUpRequest the signUpRequest entity
     * @return userDto
     */
    UserDTO saveUser(SignUpRequest signUpRequest);

    /**
     * Checks if the user by the given parameter exists in the database.
     *
     * @param username the given username
     * @return true if the user exists, false otherwise
     */
    Boolean existsByUsername(String username);
}
