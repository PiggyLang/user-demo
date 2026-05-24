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

    User createUser(UserCreateRequest request);

    User getUserById(Long id);

    User updateUser(Long id, UserUpdateRequest request);

    void deleteUser(Long id);

    IPage<User> pageUsers(UserQueryRequest request);
}
