package com.example.bcsd_studium.dto;

import java.time.LocalDateTime;

public record ExamUpdateRequest(
        String title,
        String description,
        LocalDateTime startTime,
        LocalDateTime endTime
) {}
