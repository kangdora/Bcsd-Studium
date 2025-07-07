package com.example.bcsd_studium.controller;

import com.example.bcsd_studium.dto.ExamDetailDto;
import com.example.bcsd_studium.dto.ExamSummaryDto;
import com.example.bcsd_studium.service.ExamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/exams")
@RequiredArgsConstructor
public class ExamController {

    private final ExamService examService;

    @GetMapping
    public ResponseEntity<List<ExamSummaryDto>> getAllExams() {
        return ResponseEntity.ok(examService.getAllExams());
    }

    @GetMapping("/{examId}")
    public ResponseEntity<ExamDetailDto> getExam(@PathVariable Long examId) {
        return ResponseEntity.ok(examService.getExamDetail(examId));
    }
}
