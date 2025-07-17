package com.example.bcsd_studium.service;

import com.example.bcsd_studium.domain.entity.Answer;
import com.example.bcsd_studium.domain.entity.Exam;
import com.example.bcsd_studium.domain.entity.User;
import com.example.bcsd_studium.domain.repository.AnswerRepository;
import com.example.bcsd_studium.domain.repository.CommentRepository;
import com.example.bcsd_studium.domain.repository.ExamRepository;
import com.example.bcsd_studium.domain.repository.UserRepository;
import com.example.bcsd_studium.dto.ExamDetailDto;
import com.example.bcsd_studium.dto.ExamSummaryDto;
import com.example.bcsd_studium.exception.ExamNotFoundException;
import com.example.bcsd_studium.exception.ExamTimeException;
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
    private final CommentRepository commentRepository;

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
        LocalDateTime now = LocalDateTime.now();
        if (now.isBefore(exam.getStartTime()) || now.isAfter(exam.getEndTime())) {
            throw new ExamTimeException("시험 시간이 아닙니다.");
        }

        Answer answer = getOrCreateAnswer(examId);
        answer.setAnswerMap(answerMapJson);
        answerRepository.save(answer);
    }

    public void submitExam(Long examId, LocalDateTime submittedAt) {
        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new ExamNotFoundException("해당 시험을 찾을 수 없습니다."));
        LocalDateTime now = submittedAt == null ? LocalDateTime.now() : submittedAt;
        if (now.isBefore(exam.getStartTime())) {
            throw new ExamTimeException("시험 시작 전에는 제출할 수 없습니다.");
        }

        Answer answer = getOrCreateAnswer(examId);
        if (answer.getSubmittedAt() == null) {
            answer.setSubmittedAt(now);
            answerRepository.save(answer);
        }
    }

    private Answer getOrCreateAnswer(Long examId) {
        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new ExamNotFoundException("해당 시험을 찾을 수 없습니다."));
        return answerRepository.findTopByExamIdOrderByIdDesc(examId)
                .orElse(Answer.builder().exam(exam).build());
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

    public void updateExam(Long examId, String title, String description, LocalDateTime startTime, LocalDateTime endTime) {
        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new ExamNotFoundException("해당 시험을 찾을 수 없습니다."));

        exam.setTitle(title);
        exam.setDescription(description);
        exam.setStartTime(startTime);
        exam.setEndTime(endTime);

        examRepository.save(exam);
    }

    public void deleteExam(Long examId) {
        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new ExamNotFoundException("해당 시험을 찾을 수 없습니다."));

        commentRepository.deleteAllByExamId(examId);
        answerRepository.deleteAllByExamId(examId);

        examRepository.delete(exam);
    }
}
