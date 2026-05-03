package com.example.chatbotai.Entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity
@Table(name = "NhipHoc")
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NhipHoc {

    @Id
    @Column(name = "MaNhip")
    String maNhip;

    @Column(name = "TenNhip")
    String tenNhip;

    @ManyToOne
    @JoinColumn(name = "MaHocKy")
    HocKy hocKy;

    @Column(name = "NgayBatDau")
    LocalDate ngayBatDau;

    @Column(name = "NgayKetThuc")
    LocalDate ngayKetThuc;
}