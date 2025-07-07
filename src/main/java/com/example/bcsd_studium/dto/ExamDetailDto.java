package com.example.bcsd_studium.dto;

import com.example.bcsd_studium.domain.entity.Exam;

import java.time.LocalDateTime;
import java.util.List;

public record ExamDetailDto(
        Long id,
        String title,
        String description,
        LocalDateTime startTime,
        LocalDateTime endTime,
        List<QuestionDto> questions
) {
    public static ExamDetailDto from(Exam exam, boolean includeQuestions) {
        List<QuestionDto> questionDtos = includeQuestions
                ? exam.getQuestions().stream().map(QuestionDto::from).toList()
                : List.of();
        return new ExamDetailDto(
                exam.getId(),
                exam.getTitle(),
                exam.getDescription(),
                exam.getStartTime(),
                exam.getEndTime(),
                questionDtos
        );
    }
}