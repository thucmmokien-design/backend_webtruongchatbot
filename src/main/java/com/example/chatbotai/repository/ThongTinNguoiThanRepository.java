package com.example.chatbotai.repository;

import com.example.chatbotai.Entity.ThongTinNguoiThan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ThongTinNguoiThanRepository extends JpaRepository<ThongTinNguoiThan, Integer> {
    @Query("SELECT t FROM ThongTinNguoiThan t WHERE t.sinhVien.maSV = :maSV")
    List<ThongTinNguoiThan> findByMaSV(@Param("maSV") String maSV);
}
