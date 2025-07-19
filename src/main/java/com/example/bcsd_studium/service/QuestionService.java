package com.example.bcsd_studium.service;

import com.example.bcsd_studium.domain.entity.Exam;
import com.example.bcsd_studium.domain.entity.Question;
import com.example.bcsd_studium.domain.entity.QuestionCategory;
import com.example.bcsd_studium.domain.entity.QuestionType;
import com.example.bcsd_studium.domain.repository.ExamRepository;
import com.example.bcsd_studium.domain.repository.QuestionRepository;
import com.example.bcsd_studium.exception.ExamNotFoundException;
import com.example.bcsd_studium.exception.QuestionNotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final ExamRepository examRepository;
    private final QuestionRepository questionRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public Long createQuestion(Long examId, String type, String content, String category, List<String> choices, Integer answer) {
        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new ExamNotFoundException("해당 시험을 찾을 수 없습니다."));

        QuestionType questionType = QuestionType.valueOf(type.toUpperCase());
        QuestionCategory questionCategory = QuestionCategory.valueOf(category.toUpperCase());
        String choiceJson = null;
        if (choices != null) {
            try {
                choiceJson = objectMapper.writeValueAsString(choices);
            } catch (JsonProcessingException ignored) {
            }
        }

        Question question = Question.builder()
                .exam(exam)
                .type(questionType)
                .category(questionCategory)
                .content(content)
                .choices(choiceJson)
                .answer(answer)
                .build();
        questionRepository.save(question);
        return question.getId();
    }

    public void updateQuestion(Long examId, Long questionId, String type, String content, List<String> choices, Integer answer) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new QuestionNotFoundException("해당 문제를 찾을 수 없습니다."));

        if (!question.getExam().getId().equals(examId)) {
            throw new QuestionNotFoundException("해당 문제를 찾을 수 없습니다.");
        }

        question.setType(QuestionType.valueOf(type.toUpperCase()));
        question.setContent(content);
        if (choices != null) {
            try {
                question.setChoices(objectMapper.writeValueAsString(choices));
            } catch (JsonProcessingException ignored) {
            }
        } else {
            question.setChoices(null);
        }
        question.setAnswer(answer);
        questionRepository.save(question);
    }

    public void deleteQuestion(Long questionId) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new QuestionNotFoundException("해당 문제를 찾을 수 없습니다."));
        questionRepository.delete(question);
    }
}
