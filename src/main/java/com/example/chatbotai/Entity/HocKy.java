package com.example.chatbotai.Entity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "HocKy")
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HocKy {

    @Id
    @Column(name = "MaHocKy")
    String maHocKy;

    @Column(name = "TenHocKy")
    String tenHocKy;

    @Column(name = "NamHoc")
    String namHoc;

    @Column(name = "NgayBatDau")
    String ngayBatDau;

    @Column(name = "NgayKetThuc")
    String ngayKetThuc;
}