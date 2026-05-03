package com.example.chatbotai.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "Khoa")
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Khoa {
    @Id
    @Column(name = "MaKhoa")
    String maKhoa;

    @Column(name = "TenKhoa")
    String tenKhoa;

    @Column(name = "MoTa")
    String moTa;
}
