package com.example.chatbotai.repository;

import com.example.chatbotai.Entity.LichThi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LichThiRepository extends JpaRepository<LichThi, String> {
    
    @Query("SELECT lt FROM LichThi lt " +
           "JOIN lt.lopHocPhan lhp " +
           "JOIN DangKyHoc dkh ON dkh.lopHocPhan.maLopHP = lhp.maLopHP " +
           "WHERE dkh.sinhVien.maSV = :maSV " +
           "ORDER BY lt.ngayThi, lt.gioBatDau")
    List<LichThi> findByMaSinhVien(@Param("maSV") String maSV);
    
    @Query("SELECT lt FROM LichThi lt " +
           "JOIN lt.lopHocPhan lhp " +
           "JOIN lhp.hocKy hk " +
           "JOIN DangKyHoc dkh ON dkh.lopHocPhan.maLopHP = lhp.maLopHP " +
           "WHERE dkh.sinhVien.maSV = :maSV " +
           "AND hk.maHocKy = :maHocKy " +
           "AND (:namHoc IS NULL OR hk.namHoc = :namHoc) " +
           "ORDER BY lt.ngayThi, lt.gioBatDau")
    List<LichThi> findByMaSinhVienAndHocKy(@Param("maSV") String maSV, 
                                            @Param("maHocKy") String maHocKy,
                                            @Param("namHoc") String namHoc);
}
