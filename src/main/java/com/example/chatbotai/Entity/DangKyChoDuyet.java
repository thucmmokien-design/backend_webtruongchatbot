package com.example.chatbotai.Entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Table(name = "DangKyChoDuyet")
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DangKyChoDuyet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    Integer id;

    @ManyToOne
    @JoinColumn(name = "MaSV")
    SinhVien sinhVien;

    @ManyToOne
    @JoinColumn(name = "MaMo")
    LopHocDuocMo lopHocDuocMo;

    @Column(name = "NgayDangKy")
    LocalDateTime ngayDangKy;

    @Column(name = "TrangThai", length = 50)
    String trangThai; // Chờ duyệt / Đã duyệt / Từ chối
}
