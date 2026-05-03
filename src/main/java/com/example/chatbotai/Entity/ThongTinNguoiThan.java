package com.example.chatbotai.Entity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Entity
@Table(name = "ThongTinNguoiThan")
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ThongTinNguoiThan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    Integer id;

    @ManyToOne
    @JoinColumn(name = "MaSV")
    SinhVien sinhVien;

    @Column(name = "HoTen")
    String hoTen;

    @Column(name = "NgaySinh")
    LocalDate ngaySinh;

    @Column(name = "DienThoai")
    String dienThoai;

    @Column(name = "SoCCCD")
    String soCCCD;

    @Column(name = "HoKhauThuongTru")
    String hoKhauThuongTru;

    @Column(name = "MoiQuanHe")
    String moiQuanHe;
}