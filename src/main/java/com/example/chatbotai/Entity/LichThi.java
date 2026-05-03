package com.example.chatbotai.Entity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "LichThi")
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LichThi {

    @Id
    @Column(name = "MaLichThi")
    String maLichThi;

    @ManyToOne
    @JoinColumn(name = "MaLopHP")
    LopHocPhan lopHocPhan;

    @Column(name = "NgayThi")
    LocalDate ngayThi;

    @Column(name = "GioBatDau")
    LocalTime gioBatDau;

    @Column(name = "GioKetThuc")
    LocalTime gioKetThuc;

    @Column(name = "PhongThi")
    String phongThi;

    @Column(name = "HinhThucThi")
    String hinhThucThi;
}
