package com.example.bcsd_studium.dto;

import com.example.bcsd_studium.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class UserResponseDto {
    private Long id;
    private String nickname;
    private Integer streakCount;
    private LocalDateTime createdAt;

    public static UserResponseDto from(User user) {
        return new UserResponseDto(
                user.getId(),
                user.getNickname(),
                user.getStreakCount(),
                user.getCreatedAt()
        );
    }
}
