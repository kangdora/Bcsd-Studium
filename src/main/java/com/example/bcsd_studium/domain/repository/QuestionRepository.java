package com.example.bcsd_studium.domain.repository;

import com.example.bcsd_studium.domain.entity.Question;
import com.example.bcsd_studium.domain.entity.QuestionCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findByCategoryOrderByIdAsc(QuestionCategory category);
}
