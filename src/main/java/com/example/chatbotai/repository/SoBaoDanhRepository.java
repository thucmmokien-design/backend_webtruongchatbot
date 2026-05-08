package com.example.chatbotai.repository;

import com.example.chatbotai.Entity.SoBaoDanh;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SoBaoDanhRepository extends JpaRepository<SoBaoDanh, String> {
    
    @Query("SELECT sbd FROM SoBaoDanh sbd " +
           "WHERE sbd.sinhVien.maSV = :maSV " +
           "AND sbd.lopHocPhan.maLopHP = :maLopHP")
    Optional<SoBaoDanh> findByMaSVAndMaLopHP(
            @Param("maSV") String maSV,
            @Param("maLopHP") String maLopHP
    );
}
