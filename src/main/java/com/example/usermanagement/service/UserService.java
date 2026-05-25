package com.example.usermanagement.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.usermanagement.dto.UserCreateRequest;
import com.example.usermanagement.dto.UserQueryRequest;
import com.example.usermanagement.dto.UserUpdateRequest;
import com.example.usermanagement.entity.User;

/**
 * User business service.
 *
 * @author liulang
 */
public interface UserService {

    /**
     * Creates a user from the request data.
     *
     * @param request user creation request
     * @return created user
     */
    User createUser(UserCreateRequest request);

    /**
     * Gets a user by user id.
     *
     * @param id user id
     * @return matched user, or null if not found
     */
    User getUserById(Long id);

    /**
     * Updates a user by user id with the request data.
     *
     * @param id user id
     * @param request user update request
     * @return updated user, or null if not found
     */
    User updateUser(Long id, UserUpdateRequest request);

    /**
     * Logically deletes a user by user id.
     *
     * @param id user id
     */
    void deleteUser(Long id);

    /**
     * Queries users by page with optional username fuzzy search and status filter.
     *
     * @param request user page query request
     * @return user page result
     */
    IPage<User> pageUsers(UserQueryRequest request);
}
