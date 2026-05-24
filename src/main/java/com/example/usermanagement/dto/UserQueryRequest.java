package com.example.usermanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * User page query request.
 *
 * @author liulang
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserQueryRequest {

    private long current = 1;

    private long size = 10;

    private String username;

    private Integer status;
}
