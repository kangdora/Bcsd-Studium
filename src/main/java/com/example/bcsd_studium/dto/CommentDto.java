package com.example.bcsd_studium.dto;

import com.example.bcsd_studium.domain.entity.Comment;

import java.time.LocalDateTime;

public record CommentDto(
        Long id,
        Long userId,
        String nickname,
        String content,
        LocalDateTime createdAt,
        boolean editable
) {
    public static CommentDto from(Comment comment) {
        return from(comment, false);
    }
    public static CommentDto from(Comment comment, boolean editable) {
        return new CommentDto(
                comment.getId(),
                comment.getUser().getId(),
                comment.getUser().getNickname(),
                comment.getContent(),
                comment.getCreatedAt(),
                editable
        );
    }
}
