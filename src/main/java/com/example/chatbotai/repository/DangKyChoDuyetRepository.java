package com.example.chatbotai.repository;

import com.example.chatbotai.Entity.DangKyChoDuyet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DangKyChoDuyetRepository extends JpaRepository<DangKyChoDuyet, Integer> {
    
    // Tìm đăng ký theo mã sinh viên
    @Query("SELECT dk FROM DangKyChoDuyet dk " +
           "WHERE dk.sinhVien.maSV = :maSV " +
           "ORDER BY dk.ngayDangKy DESC")
    List<DangKyChoDuyet> findByMaSV(@Param("maSV") String maSV);
    
    // Tìm đăng ký theo trạng thái
    @Query("SELECT dk FROM DangKyChoDuyet dk " +
           "WHERE dk.trangThai = :trangThai " +
           "ORDER BY dk.ngayDangKy DESC")
    List<DangKyChoDuyet> findByTrangThai(@Param("trangThai") String trangThai);
    
    // Tìm đăng ký theo mã sinh viên và trạng thái
    @Query("SELECT dk FROM DangKyChoDuyet dk " +
           "WHERE dk.sinhVien.maSV = :maSV " +
           "AND dk.trangThai = :trangThai " +
           "ORDER BY dk.ngayDangKy DESC")
    List<DangKyChoDuyet> findByMaSVAndTrangThai(
            @Param("maSV") String maSV,
            @Param("trangThai") String trangThai
    );
    
    // Kiểm tra sinh viên đã đăng ký lớp học chưa
    @Query("SELECT COUNT(dk) > 0 FROM DangKyChoDuyet dk " +
           "WHERE dk.sinhVien.maSV = :maSV " +
           "AND dk.lopHocDuocMo.maMo = :maMo " +
           "AND dk.trangThai IN ('Chờ duyệt', 'Đã duyệt')")
    boolean existsByMaSVAndMaMo(
            @Param("maSV") String maSV,
            @Param("maMo") String maMo
    );
}
