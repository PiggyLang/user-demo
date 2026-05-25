package com.example.usermanagement.dto;

import com.example.usermanagement.entity.User;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * User API response.
 *
 * @author liulang
 */
@Data
public class UserResponse {

    private Long id;

    private String username;

    private String email;

    private String phone;

    private Integer status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    /**
     * Builds a user response from a user entity.
     *
     * @param user user entity
     * @return user response
     */
    public static UserResponse from(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        response.setPhone(user.getPhone());
        response.setStatus(user.getStatus());
        response.setCreatedAt(user.getCreatedAt());
        response.setUpdatedAt(user.getUpdatedAt());
        return response;
    }
}
