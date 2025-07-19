package com.example.bcsd_studium.dto;

import com.example.bcsd_studium.domain.entity.Question;

public record QuestionInfo(
        Long id,
        String type,
        String category,
        String content,
        String choices,
        Integer answer,
        String examName
) {
    public static QuestionInfo from(Question question) {
        return new QuestionInfo(
                question.getId(),
                question.getType().toString(),
                question.getCategory().toString(),
                question.getContent(),
                question.getChoices(),
                question.getAnswer(),
                question.getExam().getTitle()
        );
    }
}
