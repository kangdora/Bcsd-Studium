package com.example.bcsd_studium.service;

import com.example.bcsd_studium.domain.entity.Answer;
import com.example.bcsd_studium.domain.entity.Exam;
import com.example.bcsd_studium.domain.entity.User;
import com.example.bcsd_studium.domain.repository.AnswerRepository;
import com.example.bcsd_studium.domain.repository.ExamRepository;
import com.example.bcsd_studium.domain.repository.UserRepository;
import com.example.bcsd_studium.dto.ExamDetailDto;
import com.example.bcsd_studium.dto.ExamSummaryDto;
import com.example.bcsd_studium.exception.ExamNotFoundException;
import com.example.bcsd_studium.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExamService {

    private final ExamRepository examRepository;
    private final AnswerRepository answerRepository;
    private final UserRepository userRepository;

    public List<ExamSummaryDto> getAllExams() {
        return examRepository.findAllByOrderByIdDesc().stream()
                .map(ExamSummaryDto::from)
                .toList();
    }

    public ExamDetailDto getExamDetail(Long examId) {
        return examRepository.findById(examId)
                .map(exam -> ExamDetailDto.from(exam, LocalDateTime.now().isAfter(exam.getStartTime())))
                .orElseThrow(() -> new ExamNotFoundException("해당 시험을 찾을 수 없습니다."));
    }

    public void saveAnswers(Long examId, String answerMapJson) {
        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new ExamNotFoundException("해당 시험을 찾을 수 없습니다."));
        Answer answer = answerRepository.findTopByExamIdOrderByIdDesc(examId)
                .orElse(Answer.builder().exam(exam).build());
        answer.setAnswerMap(answerMapJson);
        answerRepository.save(answer);
    }

    public void submitExam(Long examId, LocalDateTime submittedAt) {
        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new ExamNotFoundException("해당 시험을 찾을 수 없습니다."));
        Answer answer = answerRepository.findTopByExamIdOrderByIdDesc(examId)
                .orElse(Answer.builder().exam(exam).build());
        answer.setSubmittedAt(submittedAt);
        answerRepository.save(answer);
    }

    public Long createExam(String title, String description, LocalDateTime startTime, LocalDateTime endTime) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loginId = authentication.getName();
        User user = userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new UserNotFoundException("해당 유저를 찾을 수 없습니다."));

        Exam exam = Exam.builder()
                .title(title)
                .description(description)
                .startTime(startTime)
                .endTime(endTime)
                .createdBy(user)
                .build();
        examRepository.save(exam);
        return exam.getId();
    }
}
