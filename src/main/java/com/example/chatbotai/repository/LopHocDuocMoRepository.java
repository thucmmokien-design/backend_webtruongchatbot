package com.example.chatbotai.repository;

import com.example.chatbotai.Entity.LopHocDuocMo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LopHocDuocMoRepository extends JpaRepository<LopHocDuocMo, String> {
    
    // Tìm lớp học được mở theo học kỳ và năm học
    @Query("SELECT lhm FROM LopHocDuocMo lhm " +
           "WHERE lhm.hocKy.maHocKy = :maHocKy " +
           "AND lhm.hocKy.namHoc = :namHoc " +
           "ORDER BY lhm.monHoc.tenMon")
    List<LopHocDuocMo> findByHocKyAndNamHoc(
            @Param("maHocKy") String maHocKy,
            @Param("namHoc") String namHoc
    );
    
    // Tìm lớp học được mở theo môn học
    @Query("SELECT lhm FROM LopHocDuocMo lhm " +
           "WHERE lhm.monHoc.maMon = :maMon " +
           "AND lhm.hocKy.maHocKy = :maHocKy " +
           "AND lhm.hocKy.namHoc = :namHoc")
    List<LopHocDuocMo> findByMonHocAndHocKy(
            @Param("maMon") String maMon,
            @Param("maHocKy") String maHocKy,
            @Param("namHoc") String namHoc
    );
    
    // Tìm lớp học còn chỗ trống
    @Query("SELECT lhm FROM LopHocDuocMo lhm " +
           "WHERE lhm.soLuongTrong > 0 " +
           "AND lhm.hocKy.maHocKy = :maHocKy " +
           "AND lhm.hocKy.namHoc = :namHoc " +
           "ORDER BY lhm.monHoc.tenMon")
    List<LopHocDuocMo> findAvailableClasses(
            @Param("maHocKy") String maHocKy,
            @Param("namHoc") String namHoc
    );
}
