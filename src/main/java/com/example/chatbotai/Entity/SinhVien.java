package com.example.chatbotai.Entity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Entity
@Table(name = "SinhVien")
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SinhVien {

    @Id
    @Column(name = "MaSV")
    private String maSV;

    @Column(name = "HoTen")
    private String hoTen;

    @Column(name = "NgaySinh")
    private LocalDate ngaySinh;

    @Column(name = "GioiTinh")
    private String gioiTinh;

    @Column(name = "Email")
    private String email;

    @Column(name = "DienThoai")
    private String dienThoai;

    @Column(name = "TrangThai")
    private String trangThai;

    @ManyToOne
    @JoinColumn(name = "MaLop")
    private Lop lop;
}
