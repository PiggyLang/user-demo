package com.example.usermanagement.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.usermanagement.dto.UserCreateRequest;
import com.example.usermanagement.dto.UserQueryRequest;
import com.example.usermanagement.dto.UserResponse;
import com.example.usermanagement.dto.UserUpdateRequest;
import com.example.usermanagement.entity.User;
import com.example.usermanagement.exception.BusinessException;
import com.example.usermanagement.mapper.UserMapper;
import com.example.usermanagement.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * User business service implementation.
 *
 * @author liulang
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    /**
     * Creates a user from the request data.
     *
     * @param request user creation request
     * @return created user response
     */
    @Override
    public UserResponse createUser(UserCreateRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setStatus(request.getStatus() == null ? 1 : request.getStatus());
        user.setDeleted(0L);
        userMapper.insert(user);
        return UserResponse.from(user);
    }

    /**
     * Gets a user by user id.
     *
     * @param id user id
     * @return matched user response
     * @throws BusinessException if user does not exist
     */
    @Override
    public UserResponse getUserById(Long id) {
        return UserResponse.from(getExistingUser(id));
    }

    /**
     * Updates a user by user id with the request data.
     *
     * @param id user id
     * @param request user update request
     * @return updated user response
     * @throws BusinessException if user does not exist
     */
    @Override
    public UserResponse updateUser(Long id, UserUpdateRequest request) {
        User user = getExistingUser(id);
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setStatus(request.getStatus());
        userMapper.updateById(user);
        return UserResponse.from(user);
    }

    /**
     * Logically deletes a user by user id.
     *
     * @param id user id
     * @throws BusinessException if user does not exist
     */
    @Override
    public void deleteUser(Long id) {
        int affectedRows = userMapper.deleteById(id);
        if (affectedRows == 0) {
            throw new BusinessException(404, "User not found");
        }
    }

    /**
     * Queries users by page with optional username fuzzy search and status filter.
     *
     * @param request user page query request
     * @return user page result
     */
    @Override
    public IPage<UserResponse> pageUsers(UserQueryRequest request) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<User>()
                .like(StringUtils.hasText(request.getUsername()), User::getUsername, request.getUsername())
                .eq(request.getStatus() != null, User::getStatus, request.getStatus())
                .orderByDesc(User::getId);
        return userMapper.selectPage(new Page<User>(request.getCurrent(), request.getSize()), wrapper)
                .convert(UserResponse::from);
    }

    /**
     * Gets an existing user entity by user id.
     *
     * @param id user id
     * @return existing user entity
     * @throws BusinessException if user does not exist
     */
    private User getExistingUser(Long id) {
        User user = userMapper.selectById(id);
        if (user == null) {
            throw new BusinessException(404, "User not found");
        }
        return user;
    }
}
