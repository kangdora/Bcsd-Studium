package com.example.bcsd_studium.dto;

public record QuestionSummary(
        Long id,
        String type,
        String category,
        String examId
) {
}
