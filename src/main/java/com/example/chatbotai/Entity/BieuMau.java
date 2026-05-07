package com.example.chatbotai.Entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Table(name = "BieuMau")
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BieuMau {

    @Id
    @Column(name = "MaBieuMau", length = 10)
    String maBieuMau;

    @Column(name = "TenBieuMau", length = 255)
    String tenBieuMau;

    @Column(name = "MoTa", length = 500)
    String moTa;

    @Column(name = "LinkDrive", length = 1000)
    String linkDrive;

    @Column(name = "LoaiBieuMau", length = 100)
    String loaiBieuMau; // Đơn từ / Quy chế / Tài liệu

    @Column(name = "NgayTao")
    LocalDateTime ngayTao;
}
