package com.example.bcsd_studium.controller;

import com.example.bcsd_studium.dto.LoginRequest;
import com.example.bcsd_studium.dto.SignupRequest;
import com.example.bcsd_studium.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<Map<String, String>> signup(@RequestBody SignupRequest request) {
        userService.signUp(request.loginId(), request.email(), request.password(), request.nickname());
        return ResponseEntity.ok(Map.of("message", "회원가입 성공"));
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequest request) {
        String token = userService.login(request.email(), request.password());
        return ResponseEntity.ok(Map.of("accessToken", token));
    }
}