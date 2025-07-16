package com.example.bcsd_studium.service;

import com.example.bcsd_studium.config.JwtTokenProvider;
import com.example.bcsd_studium.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    public String login(String email, String password) {
        User user = userService.authenticate(email, password);
        return jwtTokenProvider.createToken(user.getLoginId());
    }
}
