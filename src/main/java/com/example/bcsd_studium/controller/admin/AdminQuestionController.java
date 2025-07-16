package com.example.bcsd_studium.controller.admin;

import com.example.bcsd_studium.dto.MessageResponse;
import com.example.bcsd_studium.dto.QuestionCreateRequest;
import com.example.bcsd_studium.dto.QuestionCreateResponse;
import com.example.bcsd_studium.dto.QuestionUpdateRequest;
import com.example.bcsd_studium.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminQuestionController {

    private final QuestionService questionService;

    @PostMapping("/exams/{examId}/questions")
    public ResponseEntity<QuestionCreateResponse> createQuestion(@PathVariable Long examId,
                                                                 @RequestBody QuestionCreateRequest request) {
        Long id = questionService.createQuestion(examId,
                request.type(),
                request.content(),
                request.choices(),
                request.answer());
        return ResponseEntity.ok(QuestionCreateResponse.of(id, "문제가 추가되었습니다."));
    }

    @PutMapping("/exams/{examId}/questions/{questionId}")
    public ResponseEntity<MessageResponse> updateQuestion(@PathVariable Long examId,
                                                          @PathVariable Long questionId,
                                                          @RequestBody QuestionUpdateRequest request) {
        questionService.updateQuestion(examId, questionId,
                request.type(),
                request.content(),
                request.choices(),
                request.answer());
        return ResponseEntity.ok(MessageResponse.of("문제가 수정되었습니다."));
    }

    @DeleteMapping("/questions/{questionId}")
    public ResponseEntity<MessageResponse> deleteQuestion(@PathVariable Long questionId) {
        questionService.deleteQuestion(questionId);
        return ResponseEntity.ok(MessageResponse.of("문제가 삭제되었습니다."));
    }
}