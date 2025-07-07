package com.example.bcsd_studium.service;

import com.example.bcsd_studium.domain.repository.ExamRepository;
import com.example.bcsd_studium.dto.ExamSummaryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExamService {

    private ExamRepository examRepository;

    public List<ExamSummaryDto> getAllExams() {
        return examRepository.findAllByOrderByIdDesc().stream()
                .map(ExamSummaryDto::from)
                .toList();
    }
}
