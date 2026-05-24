package com.example.usermanagement.dto;

import lombok.Data;

@Data
public class UserCreateRequest {

    private String username;

    private String email;

    private String phone;

    private Integer status;
}
