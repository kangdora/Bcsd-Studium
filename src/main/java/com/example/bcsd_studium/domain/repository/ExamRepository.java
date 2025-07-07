package com.example.bcsd_studium.domain.repository;

import com.example.bcsd_studium.domain.entity.Exam;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExamRepository extends JpaRepository<Exam, Long> {
    List<Exam> findAllByOrderByIdDesc();
}
