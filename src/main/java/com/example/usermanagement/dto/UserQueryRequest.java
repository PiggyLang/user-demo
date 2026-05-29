package com.example.usermanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

/**
 * User page query request.
 *
 * @author liulang
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserQueryRequest {

    @Min(value = 1, message = "current must be greater than or equal to 1")
    @Max(value = 1000, message = "current must be less than or equal to 1000")
    private long current = 1;

    @Min(value = 1, message = "size must be greater than or equal to 1")
    @Max(value = 100, message = "size must be less than or equal to 100")
    private long size = 10;

    @Size(max = 64, message = "username length must be less than or equal to 64")
    private String username;

    @Min(value = 0, message = "status must be 0 or 1")
    @Max(value = 1, message = "status must be 0 or 1")
    private Integer status;
}
