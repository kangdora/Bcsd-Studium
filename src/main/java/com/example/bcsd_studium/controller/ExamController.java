package com.example.bcsd_studium.controller;

import com.example.bcsd_studium.dto.AnswerSaveRequest;
import com.example.bcsd_studium.dto.ExamDetailDto;
import com.example.bcsd_studium.dto.ExamSubmitRequest;
import com.example.bcsd_studium.dto.ExamSummaryDto;
import com.example.bcsd_studium.exception.AnswerProcessingException;
import com.example.bcsd_studium.service.ExamService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/exams")
@RequiredArgsConstructor
public class ExamController {

    private final ExamService examService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping
    public ResponseEntity<List<ExamSummaryDto>> getAllExams() {
        return ResponseEntity.ok(examService.getAllExams());
    }

    @GetMapping("/{examId}")
    public ResponseEntity<ExamDetailDto> getExam(@PathVariable Long examId) {
        return ResponseEntity.ok(examService.getExamDetail(examId));
    }

    @PostMapping("/{examId}/answers")
    public ResponseEntity<Map<String, String>> saveAnswers(@PathVariable Long examId,
                                                           @RequestBody AnswerSaveRequest request) {
        try {
            String json = objectMapper.writeValueAsString(request.answerMap());
            examService.saveAnswers(examId, json);
        } catch (JsonProcessingException e) {
            throw new AnswerProcessingException("답안을 처리하는 데 실패했습니다.");
        }
        return ResponseEntity.ok(Map.of("message", "답안이 저장되었습니다."));
    }

    @PostMapping("/{examId}/submit")
    public ResponseEntity<Map<String, String>> submitExam(@PathVariable Long examId,
                                                          @RequestBody ExamSubmitRequest request) {
        examService.submitExam(examId, request.submittedAt());
        return ResponseEntity.ok(Map.of("message", "제출 완료되었습니다."));
    }
}
