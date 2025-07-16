package com.example.bcsd_studium.controller.admin;

import com.example.bcsd_studium.dto.ExamCreateRequest;
import com.example.bcsd_studium.dto.ExamCreateResponse;
import com.example.bcsd_studium.service.ExamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/exams")
@RequiredArgsConstructor
public class AdminExamController {

    private final ExamService examService;

    @PostMapping
    public ResponseEntity<ExamCreateResponse> createExam(@RequestBody ExamCreateRequest request) {
        Long id = examService.createExam(
                request.title(),
                request.description(),
                request.startTime(),
                request.endTime()
        );
        return ResponseEntity.ok(ExamCreateResponse.of(id, "시험이 생성되었습니다."));
    }

    @PutMapping("/{examId}")
    public ResponseEntity<MessageResponse> updateExam(@PathVariable Long examId,
                                                      @RequestBody ExamUpdateRequest request) {
        examService.updateExam(
                examId,
                request.title(),
                request.description(),
                request.startTime(),
                request.endTime()
        );
        return ResponseEntity.ok(MessageResponse.of("시험 정보가 수정되었습니다."));
    }
}