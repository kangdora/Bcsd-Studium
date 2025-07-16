package com.example.bcsd_studium.domain.repository;

import com.example.bcsd_studium.domain.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByExamIdOrderByIdAsc(Long examId);
}
