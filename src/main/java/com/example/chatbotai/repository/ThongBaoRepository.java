package com.example.chatbotai.repository;

import com.example.chatbotai.Entity.ThongBao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ThongBaoRepository extends JpaRepository<ThongBao, String> {
    
    // Lấy tất cả thông báo, sắp xếp theo ngày đăng mới nhất
    @Query("SELECT tb FROM ThongBao tb ORDER BY tb.ngayDang DESC")
    List<ThongBao> findAllOrderByNgayDangDesc();
    
    // Lấy thông báo theo sinh viên (dựa vào khoa và lớp)
    @Query("SELECT tb FROM ThongBao tb " +
           "WHERE tb.phamVi = 'Tất cả' " +
           "OR (tb.phamVi = 'Khoa' AND tb.khoa.maKhoa = :maKhoa) " +
           "OR (tb.phamVi = 'Lớp' AND tb.lop.maLop = :maLop) " +
           "ORDER BY tb.ngayDang DESC")
    List<ThongBao> findByMaKhoaAndMaLop(@Param("maKhoa") String maKhoa, @Param("maLop") String maLop);
    
    // Lấy thông báo theo tag
    @Query("SELECT tb FROM ThongBao tb WHERE tb.tag = :tag ORDER BY tb.ngayDang DESC")
    List<ThongBao> findByTag(@Param("tag") String tag);
}
