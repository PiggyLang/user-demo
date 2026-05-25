package com.example.usermanagement.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.usermanagement.common.Result;
import com.example.usermanagement.dto.UserCreateRequest;
import com.example.usermanagement.dto.UserQueryRequest;
import com.example.usermanagement.dto.UserResponse;
import com.example.usermanagement.dto.UserUpdateRequest;
import com.example.usermanagement.exception.BusinessException;
import com.example.usermanagement.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

/**
 * User API controller.
 *
 * @author liulang
 */
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Validated
public class UserController {

    private final UserService userService;

    /**
     * Creates a user from request body data.
     *
     * @param request user creation request
     * @return created user response
     */
    @PostMapping
    public Result<UserResponse> create(@Valid @RequestBody UserCreateRequest request) {
        return Result.success(userService.createUser(request));
    }

    /**
     * Gets a user by user id.
     *
     * @param id user id
     * @return matched user response
     * @throws BusinessException if user does not exist
     */
    @GetMapping("/{id}")
    public Result<UserResponse> getById(@Min(value = 1, message = "id must be greater than or equal to 1")
                                        @PathVariable Long id) {
        return Result.success(userService.getUserById(id));
    }

    /**
     * Updates a user by user id with request body data.
     *
     * @param id user id
     * @param request user update request
     * @return updated user response
     * @throws BusinessException if user does not exist
     */
    @PutMapping("/{id}")
    public Result<UserResponse> update(@Min(value = 1, message = "id must be greater than or equal to 1")
                                       @PathVariable Long id,
                                       @Valid @RequestBody UserUpdateRequest request) {
        return Result.success(userService.updateUser(id, request));
    }

    /**
     * Logically deletes a user by user id.
     *
     * @param id user id
     * @return successful response without data
     * @throws BusinessException if user does not exist
     */
    @DeleteMapping("/{id}")
    public Result<Void> delete(@Min(value = 1, message = "id must be greater than or equal to 1")
                               @PathVariable Long id) {
        userService.deleteUser(id);
        return Result.success();
    }

    /**
     * Queries users by page with optional username fuzzy search and status filter.
     *
     * @param current current page number
     * @param size page size
     * @param username username fuzzy search keyword
     * @param status exact user status filter
     * @return user page response
     */
    @GetMapping
    public Result<IPage<UserResponse>> page(
            @Min(value = 1, message = "current must be greater than or equal to 1")
            @RequestParam(defaultValue = "1") long current,
            @Min(value = 1, message = "size must be greater than or equal to 1")
            @Max(value = 100, message = "size must be less than or equal to 100")
            @RequestParam(defaultValue = "10") long size,
            @Size(max = 64, message = "username length must be less than or equal to 64")
            @RequestParam(required = false) String username,
            @Min(value = 0, message = "status must be 0 or 1")
            @Max(value = 1, message = "status must be 0 or 1")
            @RequestParam(required = false) Integer status) {
        UserQueryRequest request = new UserQueryRequest(current, size, username, status);
        return Result.success(userService.pageUsers(request));
    }
}
