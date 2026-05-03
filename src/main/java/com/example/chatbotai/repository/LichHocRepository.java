package com.example.chatbotai.repository;

import com.example.chatbotai.Entity.LichHoc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LichHocRepository extends JpaRepository<LichHoc, String> {
    
    @Query("SELECT lh FROM LichHoc lh WHERE lh.lopHocPhan.maLopHP = :maLopHP")
    List<LichHoc> findByMaLopHP(@Param("maLopHP") String maLopHP);
    
    @Query("SELECT lh FROM LichHoc lh WHERE lh.thu = :thu")
    List<LichHoc> findByThu(@Param("thu") Integer thu);
    
    @Query("SELECT lh FROM LichHoc lh WHERE lh.lopHocPhan.maLopHP IN :maLopHPList")
    List<LichHoc> findByMaLopHPIn(@Param("maLopHPList") List<String> maLopHPList);
    
    @Query("SELECT lh FROM LichHoc lh " +
           "JOIN lh.lopHocPhan lhp " +
           "JOIN DangKyHoc dkh ON dkh.lopHocPhan.maLopHP = lhp.maLopHP " +
           "WHERE dkh.sinhVien.maSV = :maSV " +
           "AND lh.ngayBatDau <= :ngayKetThuc " +
           "AND lh.ngayKetThuc >= :ngayBatDau " +
           "ORDER BY lh.thu, lh.tietBatDau")
    List<LichHoc> findByMaSVAndDateRange(@Param("maSV") String maSV,
                                          @Param("ngayBatDau") java.time.LocalDate ngayBatDau,
                                          @Param("ngayKetThuc") java.time.LocalDate ngayKetThuc);
    
    @Query("SELECT lh FROM LichHoc lh " +
           "JOIN lh.lopHocPhan lhp " +
           "JOIN lhp.hocKy hk " +
           "JOIN DangKyHoc dkh ON dkh.lopHocPhan.maLopHP = lhp.maLopHP " +
           "WHERE dkh.sinhVien.maSV = :maSV " +
           "AND hk.maHocKy = :maHocKy " +
           "AND (:namHoc IS NULL OR hk.namHoc = :namHoc) " +
           "ORDER BY lh.thu, lh.tietBatDau")
    List<LichHoc> findByMaSVAndHocKy(@Param("maSV") String maSV,
                                      @Param("maHocKy") String maHocKy,
                                      @Param("namHoc") String namHoc);
    
    @Query("SELECT lh FROM LichHoc lh " +
           "JOIN lh.lopHocPhan lhp " +
           "JOIN lhp.hocKy hk " +
           "JOIN DangKyHoc dkh ON dkh.lopHocPhan.maLopHP = lhp.maLopHP " +
           "JOIN NhipHoc nh ON nh.hocKy.maHocKy = hk.maHocKy " +
           "WHERE dkh.sinhVien.maSV = :maSV " +
           "AND hk.maHocKy = :maHocKy " +
           "AND (:namHoc IS NULL OR hk.namHoc = :namHoc) " +
           "AND nh.maNhip = :maNhip " +
           "AND lh.ngayBatDau <= nh.ngayKetThuc " +
           "AND lh.ngayKetThuc >= nh.ngayBatDau " +
           "ORDER BY lh.thu, lh.tietBatDau")
    List<LichHoc> findByMaSVAndHocKyAndNhipHoc(@Param("maSV") String maSV,
                                                 @Param("maHocKy") String maHocKy,
                                                 @Param("namHoc") String namHoc,
                                                 @Param("maNhip") String maNhip);
}
