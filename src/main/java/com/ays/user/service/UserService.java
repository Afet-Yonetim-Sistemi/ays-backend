package com.ays.user.service;

import com.ays.common.model.AysPage;
import com.ays.user.controller.dto.request.UserListRequest;
import com.ays.user.controller.dto.request.UserSaveRequest;
import com.ays.user.controller.dto.request.UserUpdateRequest;
import com.ays.user.model.User;

/**
 * User service to perform user related business operations.
 */
public interface UserService {
    /**
     * Saves a saveRequest to the database.
     *
     * @param saveRequest the UserSaveRequest entity
     */
    void saveUser(UserSaveRequest saveRequest);

    /**
     * Checks if the user by the given parameter exists in the database.
     *
     * @param listRequest covering page and pageSize
     * @return User list
     */
    AysPage<User> getAllUsers(UserListRequest listRequest);

    /**
     * Get User by User ID
     *
     * @param id the given User ID
     * @return User
     */
    User getUserById(String id);

    /**
     * Delete Soft User by User ID
     *
     * @param id the given User ID
     */
    void deleteUser(String id);


    /**
     * Update User by User ID
     *
     * @param updateRequest the given UserUpdateRequest object
     */
    void updateUser(UserUpdateRequest updateRequest);
}
