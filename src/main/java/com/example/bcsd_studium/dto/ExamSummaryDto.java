package com.example.bcsd_studium.dto;

import com.example.bcsd_studium.domain.entity.Exam;

import java.time.LocalDateTime;

public record ExamSummaryDto(
        Long id,
        String title,
        LocalDateTime startTime,
        LocalDateTime endTime,
        String createdBy
) {
    public static ExamSummaryDto from(Exam exam) {
        return new ExamSummaryDto(
                exam.getId(),
                exam.getTitle(),
                exam.getStartTime(),
                exam.getEndTime(),
                exam.getCreatedBy().getNickname()
        );
    }
}
