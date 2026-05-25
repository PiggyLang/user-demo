package com.example.usermanagement.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Unified API response wrapper.
 *
 * @author liulang
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {

    private Integer code;

    private String message;

    private T data;

    /**
     * Creates a successful response with data.
     *
     * @param data response data
     * @param <T> response data type
     * @return successful response
     */
    public static <T> Result<T> success(T data) {
        return new Result<T>(200, "success", data);
    }

    /**
     * Creates a successful response without data.
     *
     * @param <T> response data type
     * @return successful response
     */
    public static <T> Result<T> success() {
        return new Result<T>(200, "success", null);
    }

    /**
     * Creates a failed response with code and message.
     *
     * @param code error code
     * @param message error message
     * @param <T> response data type
     * @return failed response
     */
    public static <T> Result<T> failure(Integer code, String message) {
        return new Result<T>(code, message, null);
    }
}
