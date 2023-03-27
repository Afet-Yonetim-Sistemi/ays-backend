package com.ays.backend.user.service;

import com.ays.backend.user.controller.payload.request.AdminLoginRequest;
import com.ays.backend.user.controller.payload.request.AdminRegisterRequest;
import com.ays.backend.user.service.dto.UserDTO;
import com.ays.backend.user.service.dto.UserTokenDTO;

/**
 * Auth service to perform user related authentication operations.
 */
public interface AuthService {

    /**
     * Register to platform.
     *
     * @param registerRequest the registerRequest entity
     * @return UserDTO
     */
    UserDTO register(AdminRegisterRequest registerRequest);

    /**
     * Login to platform.
     *
     * @param loginRequest the loginRequest entity
     * @return AuthResponse
     */
    UserTokenDTO login(AdminLoginRequest loginRequest);
}
