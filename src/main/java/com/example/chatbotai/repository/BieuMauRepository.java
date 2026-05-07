package com.example.chatbotai.repository;

import com.example.chatbotai.Entity.BieuMau;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BieuMauRepository extends JpaRepository<BieuMau, String> {
    
    // Tìm biểu mẫu theo loại
    @Query("SELECT bm FROM BieuMau bm WHERE bm.loaiBieuMau = :loai ORDER BY bm.ngayTao DESC")
    List<BieuMau> findByLoaiBieuMau(@Param("loai") String loai);
    
    // Tìm tất cả biểu mẫu, sắp xếp theo ngày tạo mới nhất
    @Query("SELECT bm FROM BieuMau bm ORDER BY bm.ngayTao DESC")
    List<BieuMau> findAllOrderByNgayTaoDesc();
}
