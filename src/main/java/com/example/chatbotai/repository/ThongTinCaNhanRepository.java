package com.example.chatbotai.repository;

import com.example.chatbotai.Entity.ThongTinCaNhan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ThongTinCaNhanRepository extends JpaRepository<ThongTinCaNhan, String> {
    Optional<ThongTinCaNhan> findByMaSV(String maSV);
}
