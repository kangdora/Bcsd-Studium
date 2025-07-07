package com.example.bcsd_studium.service;

import com.example.bcsd_studium.domain.entity.User;
import com.example.bcsd_studium.domain.repository.UserRepository;
import com.example.bcsd_studium.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserResponseDto getUserByLoginId(String loginId) {
        User user = userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new RuntimeException("해당 유저를 찾을 수 없습니다."));
        return UserResponseDto.from(user);
    }
}
