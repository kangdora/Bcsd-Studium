package com.example.bcsd_studium.controller;

import com.example.bcsd_studium.dto.*;
import com.example.bcsd_studium.exception.AnswerProcessingException;
import com.example.bcsd_studium.service.CommentService;
import com.example.bcsd_studium.service.ExamService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exams")
@RequiredArgsConstructor
public class ExamController {

    private final ExamService examService;
    private final CommentService commentService;
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
    public ResponseEntity<MessageResponse> saveAnswers(@PathVariable Long examId,
                                                           @RequestBody AnswerSaveRequest request) {
        try {
            String json = objectMapper.writeValueAsString(request.answerMap());
            examService.saveAnswers(examId, json);
        } catch (JsonProcessingException e) {
            throw new AnswerProcessingException("답안을 처리하는 데 실패했습니다.");
        }
        return ResponseEntity.ok(MessageResponse.of("답안이 저장되었습니다."));
    }

    @PostMapping("/{examId}/submit")
    public ResponseEntity<MessageResponse> submitExam(@PathVariable Long examId,
                                                          @RequestBody ExamSubmitRequest request) {
        examService.submitExam(examId, request.submittedAt());
        return ResponseEntity.ok(MessageResponse.of("제출 완료되었습니다."));
    }

    @GetMapping("/{examId}/comments")
    public ResponseEntity<List<CommentDto>> getExamComments(@PathVariable Long examId) {
        return ResponseEntity.ok(commentService.getComments(examId));
    }

    @PostMapping("/{examId}/comments")
    public ResponseEntity<MessageResponse> addComment(@PathVariable Long examId,
                                                          @RequestBody CommentRequest request) {
        commentService.addComment(examId, request.content());
        return ResponseEntity.ok(MessageResponse.of("댓글이 등록되었습니다."));
    }

    @PutMapping("/{examId}/comments/{commentId}")
    public ResponseEntity<MessageResponse> updateComment(@PathVariable Long examId,
                                                         @PathVariable Long commentId,
                                                         @RequestBody CommentRequest request) {
        commentService.updateComment(examId, commentId, request.content());
        return ResponseEntity.ok(MessageResponse.of("댓글이 수정되었습니다."));
    }

    @DeleteMapping("/{examId}/comments/{commentId}")
    public ResponseEntity<MessageResponse> deleteComment(@PathVariable Long examId,
                                                         @PathVariable Long commentId) {
        commentService.deleteComment(examId, commentId);
        return ResponseEntity.ok(MessageResponse.of("댓글이 삭제되었습니다."));
    }
}
