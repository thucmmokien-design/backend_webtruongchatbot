package com.example.chatbotai.Entity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Entity
@Table(name = "LichHoc")
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LichHoc {

    @Id
    @Column(name = "MaLichHoc")
    String maLichHoc;

    @Column(name = "Thu")
    Integer thu;

    @Column(name = "TietBatDau")
    Integer tietBatDau;

    @Column(name = "TietKetThuc")
    Integer tietKetThuc;

    @Column(name = "Phong")
    String phong;

    @Column(name = "NgayBatDau")
    LocalDate ngayBatDau;

    @Column(name =  "NgayKetThuc")
    LocalDate  ngayKetThuc;

    @ManyToOne
    @JoinColumn(name = "MaLopHP")
    LopHocPhan lopHocPhan;
}
