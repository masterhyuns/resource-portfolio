package com.portfolio.resource.common.exception;

import com.portfolio.resource.common.response.ApiResponseCode;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
    private final ApiResponseCode code;

    public CustomException(ApiResponseCode code) {
        super(code.getCode());
        this.code = code;
    }
}
