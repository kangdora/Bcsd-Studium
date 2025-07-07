package com.example.bcsd_studium.dto;

import com.example.bcsd_studium.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserRankDto {
    private String nickname;
    private Integer streakCount;

    public static UserRankDto from(User user) {
        return new UserRankDto(user.getNickname(), user.getStreakCount());
    }
}
