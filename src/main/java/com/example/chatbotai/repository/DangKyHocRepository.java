package com.example.chatbotai.repository;

import com.example.chatbotai.Entity.DangKyHoc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DangKyHocRepository extends JpaRepository<DangKyHoc, Integer> {
    
    @Query("SELECT dk FROM DangKyHoc dk WHERE dk.sinhVien.maSV = :maSV")
    List<DangKyHoc> findByMaSV(@Param("maSV") String maSV);
}
