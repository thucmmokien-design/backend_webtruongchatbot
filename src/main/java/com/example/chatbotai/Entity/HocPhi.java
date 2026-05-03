package com.example.chatbotai.Entity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Entity
@Table(name = "HocPhi")
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HocPhi {

    @Id
    @Column(name = "MaHocPhi")
    String maHocPhi;

    @ManyToOne
    @JoinColumn(name = "MaSV")
    SinhVien sinhVien;

    @ManyToOne
    @JoinColumn(name = "MaHocKy")
    HocKy hocKy;

    @Column(name = "TongTien")
    BigDecimal tongTien;

    @Column(name = "MienGiam")
    BigDecimal mienGiam;

    @Column(name = "SoTienPhaiNop")
    BigDecimal soTienPhaiNop;

    @Column(name = "TrangThai")
    String trangThai;
}
