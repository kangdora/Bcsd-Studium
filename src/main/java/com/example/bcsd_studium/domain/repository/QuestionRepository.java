package com.example.bcsd_studium.domain.repository;

import com.example.bcsd_studium.domain.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}