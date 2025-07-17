package com.example.bcsd_studium.service;

import com.example.bcsd_studium.domain.entity.Answer;
import com.example.bcsd_studium.domain.repository.AnswerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ExamSubmissionScheduler {
    private final AnswerRepository answerRepository;
    private final ExamService examService;

    @Scheduled(fixedRate = 60000)
    public void submitExpiredExams() {
        LocalDateTime now = LocalDateTime.now();
        List<Answer> answers = answerRepository.findAllBySubmittedAtIsNullAndExam_EndTimeBefore(now);
        for (Answer answer : answers) {
            examService.submitExam(answer.getExam().getId(), now);
        }
    }

    @Scheduled(fixedRate = 300000)
    public void saveAnswers() {
        List<Answer> answers = answerRepository.findAll();
        for (Answer answer : answers) {
            examService.saveAnswers(answer.getExam().getId(), answer.getAnswerMap());
        }
    }
}