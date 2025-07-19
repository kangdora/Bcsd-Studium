package com.example.bcsd_studium.controller;

import com.example.bcsd_studium.dto.QuestionSummaryResponse;
import com.example.bcsd_studium.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/questions")
@RequiredArgsConstructor
public class QuestionController {
    private final QuestionService questionService;

    @GetMapping("/category/{categoryType}")
    public ResponseEntity<QuestionSummaryResponse> getQuestionByCategory(@PathVariable String categoryType) {
        return ResponseEntity.ok(questionService.getQuestionByCategory(categoryType));
    }
}
