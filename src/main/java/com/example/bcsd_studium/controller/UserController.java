package com.example.bcsd_studium.controller;

import com.example.bcsd_studium.dto.UserRankDto;
import com.example.bcsd_studium.dto.UserResponseDto;
import com.example.bcsd_studium.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{loginId}")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable String loginId) {
        return ResponseEntity.ok(userService.getUserByLoginId(loginId));
    }

    @GetMapping("/rank")
    public ResponseEntity<List<UserRankDto>> getUserRank() {
        return ResponseEntity.ok(userService.getUserRankings());
    }
}
