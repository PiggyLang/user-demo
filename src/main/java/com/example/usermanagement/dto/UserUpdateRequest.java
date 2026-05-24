package com.example.usermanagement.dto;

import lombok.Data;

/**
 * User update request.
 *
 * @author liulang
 */
@Data
public class UserUpdateRequest {

    private String username;

    private String email;

    private String phone;

    private Integer status;
}
