package com.example.chatbotai.Entity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "ThanhToan")
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ThanhToan {

    @Id
    @Column(name = "MaThanhToan")
    String maThanhToan;

    @Column(name = "SoTien")
    BigDecimal soTien;

    @Column(name = "NgayThanhToan")
    LocalDateTime ngayThanhToan;

    @Column(name = "PhuongThuc")
    String phuongThuc;

    @ManyToOne
    @JoinColumn(name = "MaHocPhi")
    HocPhi hocPhi;
}