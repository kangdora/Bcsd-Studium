package com.example.bcsd_studium.service;

import com.example.bcsd_studium.domain.entity.Comment;
import com.example.bcsd_studium.domain.entity.Exam;
import com.example.bcsd_studium.domain.entity.User;
import com.example.bcsd_studium.domain.repository.CommentRepository;
import com.example.bcsd_studium.domain.repository.ExamRepository;
import com.example.bcsd_studium.domain.repository.UserRepository;
import com.example.bcsd_studium.dto.CommentDto;
import com.example.bcsd_studium.exception.ExamNotFoundException;
import com.example.bcsd_studium.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final ExamRepository examRepository;
    private final UserRepository userRepository;

    public List<CommentDto> getComments(Long examId) {
        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new ExamNotFoundException("해당 시험을 찾을 수 없습니다."));
        return commentRepository.findByExamIdOrderByIdAsc(exam.getId()).stream()
                .map(CommentDto::from)
                .toList();
    }

    public void addComment(Long examId, String content) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loginId = authentication.getName();

        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new ExamNotFoundException("해당 시험을 찾을 수 없습니다."));
        User user = userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new UserNotFoundException("해당 유저를 찾을 수 없습니다."));
        Comment comment = Comment.builder()
                .exam(exam)
                .user(user)
                .content(content)
                .build();
        commentRepository.save(comment);
    }
}