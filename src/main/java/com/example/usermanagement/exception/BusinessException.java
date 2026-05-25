package com.example.usermanagement.exception;

/**
 * Business exception with API error code.
 *
 * @author liulang
 */
public class BusinessException extends RuntimeException {

    private final Integer code;

    /**
     * Creates a business exception with default business error code.
     *
     * @param message exception message
     */
    public BusinessException(String message) {
        this(400, message);
    }

    /**
     * Creates a business exception with custom error code.
     *
     * @param code API error code
     * @param message exception message
     */
    public BusinessException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    /**
     * Gets the API error code.
     *
     * @return API error code
     */
    public Integer getCode() {
        return code;
    }
}
