package com.example.chatbotai.repository;

import com.example.chatbotai.Entity.TaiKhoan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<TaiKhoan, String> {
    boolean existsByUsernameAndPassword(String username, String password);
    Optional<TaiKhoan> findByUsername(String username);
}
