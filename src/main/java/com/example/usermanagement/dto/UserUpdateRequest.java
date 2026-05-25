package com.example.usermanagement.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * User update request.
 *
 * @author liulang
 */
@Data
public class UserUpdateRequest {

    @NotBlank(message = "username must not be blank")
    @Size(max = 64, message = "username length must be less than or equal to 64")
    private String username;

    @Email(message = "email format is invalid")
    @Size(max = 128, message = "email length must be less than or equal to 128")
    private String email;

    @Size(max = 32, message = "phone length must be less than or equal to 32")
    private String phone;

    @NotNull(message = "status must not be null")
    @Min(value = 0, message = "status must be 0 or 1")
    @Max(value = 1, message = "status must be 0 or 1")
    private Integer status;
}
