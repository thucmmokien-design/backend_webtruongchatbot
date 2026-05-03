package com.example.chatbotai.repository;

import com.example.chatbotai.Entity.ChiTietHocPhi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChiTietHocPhiRepository extends JpaRepository<ChiTietHocPhi, Integer> {
    
    @Query("SELECT ct FROM ChiTietHocPhi ct " +
           "WHERE ct.hocPhi.sinhVien.maSV = :maSV " +
           "ORDER BY ct.hocPhi.hocKy.namHoc, ct.hocPhi.hocKy.maHocKy, ct.lopHocPhan.monHoc.tenMon")
    List<ChiTietHocPhi> findByMaSV(@Param("maSV") String maSV);
}
