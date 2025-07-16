package com.example.bcsd_studium.dto;

import java.util.List;

public record QuestionUpdateRequest(
        String type,
        String content,
        List<String> choices,
        Integer answer
) {}
