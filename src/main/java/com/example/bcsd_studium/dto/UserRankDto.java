package com.example.bcsd_studium.dto;

import com.example.bcsd_studium.domain.entity.User;

public record UserRankDto(
        String nickname,
        Integer streakCount
) {
    public static UserRankDto from(User user) {
        return new UserRankDto(user.getNickname(), user.getStreakCount());
    }
}
