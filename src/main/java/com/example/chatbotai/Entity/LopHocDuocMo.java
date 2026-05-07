package com.example.chatbotai.Entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Entity
@Table(name = "LopHocDuocMo")
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LopHocDuocMo {

    @Id
    @Column(name = "MaMo", length = 20)
    String maMo;

    @ManyToOne
    @JoinColumn(name = "MaMon")
    MonHoc monHoc;

    @ManyToOne
    @JoinColumn(name = "MaGV")
    GiangVien giangVien;

    @ManyToOne
    @JoinColumn(name = "MaHocKy")
    HocKy hocKy;

    @ManyToOne
    @JoinColumn(name = "MaNhip")
    NhipHoc nhipHoc;

    @Column(name = "NgayBatDau")
    LocalDate ngayBatDau;

    @Column(name = "NgayKetThuc")
    LocalDate ngayKetThuc;

    @Column(name = "Thu")
    Integer thu;

    @Column(name = "TietBatDau")
    Integer tietBatDau;

    @Column(name = "TietKetThuc")
    Integer tietKetThuc;

    @Column(name = "Phong", length = 50)
    String phong;

    @Column(name = "SiSoToiDa")
    Integer siSoToiDa;

    @Column(name = "SoLuongTrong")
    Integer soLuongTrong;
}
