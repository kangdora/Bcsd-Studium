package com.example.bcsd_studium.dto;

import com.example.bcsd_studium.domain.entity.Question;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Collections;
import java.util.List;

public record QuestionDto(
        Long questionNumber,
        String type,
        String content,
        List<String> choices
) {
    public static QuestionDto from(Question question) {
        List<String> choiceList = Collections.emptyList();
        if (question.getChoices() != null) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                choiceList = mapper.readValue(question.getChoices(), new TypeReference<>() {});
            } catch (Exception ignored) {
            }
        }
        return new QuestionDto(
                question.getId(),
                question.getType().name().toLowerCase(),
                question.getContent(),
                choiceList
        );
    }
}
