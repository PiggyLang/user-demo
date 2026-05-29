package com.example.usermanagement.service;

import com.example.usermanagement.common.PageResponse;
import com.example.usermanagement.dto.UserCreateRequest;
import com.example.usermanagement.dto.UserQueryRequest;
import com.example.usermanagement.dto.UserResponse;
import com.example.usermanagement.dto.UserUpdateRequest;
import com.example.usermanagement.exception.BusinessException;

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
     * @return created user response
     */
    UserResponse createUser(UserCreateRequest request);

    /**
     * Gets a user by user id.
     *
     * @param id user id
     * @return matched user response
     * @throws BusinessException if user does not exist
     */
    UserResponse getUserById(Long id);

    /**
     * Updates a user by user id with the request data.
     *
     * @param id user id
     * @param request user update request
     * @return updated user response
     * @throws BusinessException if user does not exist
     */
    UserResponse updateUser(Long id, UserUpdateRequest request);

    /**
     * Logically deletes a user by user id.
     *
     * @param id user id
     * @throws BusinessException if user does not exist
     */
    void deleteUser(Long id);

    /**
     * Queries users by page with optional username fuzzy search and status filter.
     *
     * @param request user page query request
     * @return user page result
     */
    PageResponse<UserResponse> pageUsers(UserQueryRequest request);
}
