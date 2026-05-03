package com.example.chatbotai.Entity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "MonHoc")
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MonHoc {

    @Id
    @Column(name = "MaMon")
    String maMon;

    @Column(name = "TenMon")
    String tenMon;

    @Column(name = "SoTinChi")
    Integer soTinChi;

    @Column(name = "LoaiMon")
    String LoaiMon;
}