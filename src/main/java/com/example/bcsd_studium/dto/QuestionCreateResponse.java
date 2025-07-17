package com.example.bcsd_studium.dto;

public record QuestionCreateResponse(Long id, String message) {
    public static QuestionCreateResponse of(Long id, String message) {
        return new QuestionCreateResponse(id, message);
    }
}
