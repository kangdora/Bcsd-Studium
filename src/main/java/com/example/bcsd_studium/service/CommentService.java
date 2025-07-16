package com.example.bcsd_studium.service;

import com.example.bcsd_studium.domain.entity.Exam;
import com.example.bcsd_studium.domain.repository.CommentRepository;
import com.example.bcsd_studium.domain.repository.ExamRepository;
import com.example.bcsd_studium.dto.CommentDto;
import com.example.bcsd_studium.exception.ExamNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final ExamRepository examRepository;

    public List<CommentDto> getComments(Long examId) {
        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new ExamNotFoundException("해당 시험을 찾을 수 없습니다."));
        return commentRepository.findByExamIdOrderByIdAsc(exam.getId()).stream()
                .map(CommentDto::from)
                .toList();
    }
}