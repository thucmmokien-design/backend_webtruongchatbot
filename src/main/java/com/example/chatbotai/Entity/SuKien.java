package com.example.chatbotai.Entity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Entity
@Table(name = "SuKien")
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SuKien {

    @Id
    @Column(name = "MaSuKien")
    String maSuKien;

    @Column(name = "TenSuKien")
    String tenSuKien;

    @Column(name = "NoiDung")
    String noiDung;

    @Column(name = "NgayBatDau")
    LocalDate ngayBatDau;

    @Column(name = "NgayKetThuc")
    LocalDate ngayKetThuc;

    @Column(name = "DiaDiem")
    String diaDiem;
}
