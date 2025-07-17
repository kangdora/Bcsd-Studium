package com.example.bcsd_studium.exception;

import lombok.Getter;

@Getter
public class DuplicateEmailException extends RuntimeException {
    private final ErrorCode errorCode;

    public DuplicateEmailException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
