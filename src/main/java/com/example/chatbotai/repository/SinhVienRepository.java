package com.example.chatbotai.repository;

import com.example.chatbotai.Entity.SinhVien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SinhVienRepository extends JpaRepository<SinhVien, String> {
    Optional<SinhVien> findByMaSV(String maSV);
}
