package com.example.chatbotai.repository;

import com.example.chatbotai.Entity.HocPhi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HocPhiRepository extends JpaRepository<HocPhi, String> {
    
    @Query("SELECT hp FROM HocPhi hp WHERE hp.sinhVien.maSV = :maSV ORDER BY hp.hocKy.namHoc, hp.hocKy.maHocKy")
    List<HocPhi> findByMaSV(@Param("maSV") String maSV);
    
    @Query("SELECT hp FROM HocPhi hp " +
           "WHERE hp.sinhVien.maSV = :maSV " +
           "AND hp.hocKy.maHocKy = :maHocKy " +
           "AND hp.hocKy.namHoc = :namHoc")
    HocPhi findByMaSVAndHocKyAndNamHoc(
            @Param("maSV") String maSV,
            @Param("maHocKy") String maHocKy,
            @Param("namHoc") String namHoc
    );
}
