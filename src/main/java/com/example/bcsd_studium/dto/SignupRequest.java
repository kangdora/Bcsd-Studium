package com.example.bcsd_studium.dto;

public record SignupRequest(
        String loginId,
        String email,
        String password,
        String nickname
) {}