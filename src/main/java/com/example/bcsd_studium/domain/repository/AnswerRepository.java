package com.example.bcsd_studium.domain.repository;

import com.example.bcsd_studium.domain.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    Optional<Answer> findTopByExamIdOrderByIdDesc(Long examId);
}