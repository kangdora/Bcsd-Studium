package com.example.bcsd_studium.domain.repository;

import com.example.bcsd_studium.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByLoginId(String loginId);
    List<User> findAllByOrderByStreakCountDescCreatedAtAsc();
    Optional<User> findByEmail(String email);
}
