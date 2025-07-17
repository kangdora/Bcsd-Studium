package com.example.bcsd_studium.service;

import com.example.bcsd_studium.domain.entity.Comment;
import com.example.bcsd_studium.domain.entity.Exam;
import com.example.bcsd_studium.domain.entity.User;
import com.example.bcsd_studium.domain.repository.CommentRepository;
import com.example.bcsd_studium.domain.repository.ExamRepository;
import com.example.bcsd_studium.domain.repository.UserRepository;
import com.example.bcsd_studium.dto.CommentDto;
import com.example.bcsd_studium.exception.CommentAccessDeniedException;
import com.example.bcsd_studium.exception.CommentNotFoundException;
import com.example.bcsd_studium.exception.ExamNotFoundException;
import com.example.bcsd_studium.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final ExamRepository examRepository;
    private final UserRepository userRepository;

    public List<CommentDto> getComments(Long examId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loginId = authentication != null ? authentication.getName() : null;

        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new ExamNotFoundException("해당 시험을 찾을 수 없습니다."));

        List<Comment> comments = commentRepository.findByExamIdOrderByIdAsc(exam.getId());

        List<CommentDto> commentDtos = new ArrayList<>();
        for (Comment comment : comments) {
            boolean editable = loginId != null && loginId.equals(comment.getUser().getLoginId());
            CommentDto dto = CommentDto.from(comment, editable);
            commentDtos.add(dto);
        }

        return commentDtos;
    }


    public void addComment(Long examId, String content) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loginId = authentication.getName();

        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new ExamNotFoundException("해당 시험을 찾을 수 없습니다."));
        User user = userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new UserNotFoundException("해당 유저를 찾을 수 없습니다."));
        Comment comment = Comment.builder()
                .exam(exam)
                .user(user)
                .content(content)
                .build();
        commentRepository.save(comment);
    }

    public void updateComment(Long examId, Long commentId, String content) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loginId = authentication.getName();

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException("해당 댓글을 찾을 수 없습니다."));

        if (!comment.getExam().getId().equals(examId)) {
            throw new CommentNotFoundException("해당 댓글을 찾을 수 없습니다.");
        }

        if (!comment.getUser().getLoginId().equals(loginId)) {
            throw new CommentAccessDeniedException("댓글 수정 권한이 없습니다.");
        }

        comment.setContent(content);
        commentRepository.save(comment);
    }


    public void deleteComment(Long examId, Long commentId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loginId = authentication.getName();

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException("해당 댓글을 찾을 수 없습니다."));

        if (!comment.getExam().getId().equals(examId)) {
            throw new IllegalArgumentException("해당 시험의 댓글이 아닙니다.");
        }

        User user = userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new UserNotFoundException("해당 유저를 찾을 수 없습니다."));

        boolean isAuthor = comment.getUser().getId().equals(user.getId());
        if (!isAuthor && !user.isAdmin()) {
            throw new IllegalArgumentException("댓글을 삭제할 권한이 없습니다.");
        }

        commentRepository.delete(comment);
    }
}
