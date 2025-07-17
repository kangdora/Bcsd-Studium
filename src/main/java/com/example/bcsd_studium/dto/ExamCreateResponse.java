package com.example.bcsd_studium.dto;

public record ExamCreateResponse(Long id, String message) {
    public static ExamCreateResponse of(Long id, String message) {
        return new ExamCreateResponse(id, message);
    }
}
