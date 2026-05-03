package com.example.chatbotai.repository;

import com.example.chatbotai.Entity.BangDiem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BangDiemRepository extends JpaRepository<BangDiem, Integer> {
    
    @Query("SELECT bd FROM BangDiem bd " +
           "WHERE bd.sinhVien.maSV = :maSV " +
           "ORDER BY bd.lopHocPhan.monHoc.maMon")
    List<BangDiem> findByMaSV(@Param("maSV") String maSV);
    
    @Query("SELECT bd FROM BangDiem bd " +
           "JOIN bd.lopHocPhan lhp " +
           "JOIN lhp.hocKy hk " +
           "WHERE bd.sinhVien.maSV = :maSV " +
           "AND hk.maHocKy = :maHocKy " +
           "AND (:namHoc IS NULL OR hk.namHoc = :namHoc) " +
           "ORDER BY bd.lopHocPhan.monHoc.maMon")
    List<BangDiem> findByMaSVAndHocKy(@Param("maSV") String maSV,
                                       @Param("maHocKy") String maHocKy,
                                       @Param("namHoc") String namHoc);


}
