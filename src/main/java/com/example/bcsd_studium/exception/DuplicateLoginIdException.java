package com.example.bcsd_studium.exception;

import lombok.Getter;

@Getter
public class DuplicateLoginIdException extends RuntimeException {
    private final ErrorCode errorCode;

    public DuplicateLoginIdException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
