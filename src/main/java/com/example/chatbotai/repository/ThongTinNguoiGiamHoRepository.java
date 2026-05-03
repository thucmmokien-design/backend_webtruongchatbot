package com.example.chatbotai.repository;

import com.example.chatbotai.Entity.ThongTinNguoiGiamHo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ThongTinNguoiGiamHoRepository extends JpaRepository<ThongTinNguoiGiamHo, Integer> {
    @Query("SELECT t FROM ThongTinNguoiGiamHo t WHERE t.sinhVien.maSV = :maSV")
    List<ThongTinNguoiGiamHo> findByMaSV(@Param("maSV") String maSV);
}
