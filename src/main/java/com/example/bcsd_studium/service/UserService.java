package com.example.bcsd_studium.service;

import com.example.bcsd_studium.config.JwtTokenProvider;
import com.example.bcsd_studium.domain.entity.User;
import com.example.bcsd_studium.domain.repository.UserRepository;
import com.example.bcsd_studium.dto.UserRankDto;
import com.example.bcsd_studium.dto.UserResponseDto;
import com.example.bcsd_studium.exception.DuplicateLoginIdException;
import com.example.bcsd_studium.exception.ErrorCode;
import com.example.bcsd_studium.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

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

    public void signUp(String loginId, String email, String password, String nickname) {
        if (userRepository.findByLoginId(loginId).isPresent()) {
            throw new DuplicateLoginIdException(ErrorCode.DUPLICATE_LOGIN_ID);
        }

        User user = User.builder()
                .loginId(loginId)
                .email(email)
                .password(passwordEncoder.encode(password))
                .nickname(nickname)
                .streakCount(0)
                .build();
        userRepository.save(user);
    }

    public String login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("해당 유저를 찾을 수 없습니다."));
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        return jwtTokenProvider.createToken(user.getLoginId());
    }

    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        User user = userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getLoginId())
                .password(user.getPassword())
                .roles(user.isAdmin() ? "ADMIN" : "USER")
                .build();
    }
}
