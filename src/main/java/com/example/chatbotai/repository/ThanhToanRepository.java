package com.example.chatbotai.repository;

import com.example.chatbotai.Entity.ThanhToan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ThanhToanRepository extends JpaRepository<ThanhToan, String> {
    
    @Query("SELECT tt FROM ThanhToan tt WHERE tt.hocPhi.sinhVien.maSV = :maSV ORDER BY tt.ngayThanhToan DESC")
    List<ThanhToan> findByMaSV(@Param("maSV") String maSV);
}
