package com.example.chatbotai.Entity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Table(name = "DangKyHoc")
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DangKyHoc {

    @Id
    @Column(name = "ID")
    Integer id;

    @Column(name = "NgayDangKy")
    LocalDateTime ngayDangKy;

    @ManyToOne
    @JoinColumn(name = "MaSV")
    SinhVien sinhVien;

    @ManyToOne
    @JoinColumn(name = "MaLopHP")
    LopHocPhan lopHocPhan;
}
