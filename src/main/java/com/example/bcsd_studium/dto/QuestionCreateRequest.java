package com.example.bcsd_studium.dto;

import java.util.List;

public record QuestionCreateRequest(
        String type,
        String content,
        String category,
        List<String> choices,
        Integer answer
) {}
