package com.example.chatbotai.Entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Entity
@Table(name = "ChiTietHocPhi")
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChiTietHocPhi {

    @Id
    @Column(name = "ID")
    Integer id;

    @ManyToOne
    @JoinColumn(name = "MaHocPhi")
    HocPhi hocPhi;

    @ManyToOne
    @JoinColumn(name = "MaLopHP")
    LopHocPhan lopHocPhan;

    @Column(name = "SoTinChi")
    Integer soTinChi;

    @Column(name = "DonGia")
    BigDecimal donGia;

    @Column(name = "MienGiam")
    BigDecimal mienGiam;
}
