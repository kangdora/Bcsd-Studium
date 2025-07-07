package com.example.bcsd_studium.service;

import com.example.bcsd_studium.domain.repository.ExamRepository;
import com.example.bcsd_studium.dto.ExamDetailDto;
import com.example.bcsd_studium.dto.ExamSummaryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExamService {

    private final ExamRepository examRepository;

    public List<ExamSummaryDto> getAllExams() {
        return examRepository.findAllByOrderByIdDesc().stream()
                .map(ExamSummaryDto::from)
                .toList();
    }

    public ExamDetailDto getExamDetail(Long examId) {
        return examRepository.findById(examId)
                .map(exam -> ExamDetailDto.from(exam, LocalDateTime.now().isAfter(exam.getStartTime())))
                .orElseThrow(() -> new RuntimeException("해당 시험을 찾을 수 없습니다."));
    }
}
