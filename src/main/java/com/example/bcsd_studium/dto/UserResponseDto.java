package com.example.bcsd_studium.dto;

import com.example.bcsd_studium.domain.entity.User;

import java.time.LocalDateTime;

public record UserResponseDto(
        Long id,
        String nickname,
        Integer streakCount,
        LocalDateTime createdAt
) {
    public static UserResponseDto from(User user) {
        return new UserResponseDto(
                user.getId(),
                user.getNickname(),
                user.getStreakCount(),
                user.getCreatedAt()
        );
    }
}
