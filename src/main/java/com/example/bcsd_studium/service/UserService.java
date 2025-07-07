package com.example.bcsd_studium.service;

import com.example.bcsd_studium.domain.entity.User;
import com.example.bcsd_studium.domain.repository.UserRepository;
import com.example.bcsd_studium.dto.UserRankDto;
import com.example.bcsd_studium.dto.UserResponseDto;
import com.example.bcsd_studium.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserResponseDto getUserByLoginId(String loginId) {
        User user = userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new UserNotFoundException("해당 유저를 찾을 수 없습니다."));
        return UserResponseDto.from(user);
    }

    public List<UserRankDto> getUserRankings() {
        return userRepository.findAllByOrderByStreakCountDescCreatedAtAsc().stream()
                .map(UserRankDto::from)
                .toList();
    }
}
