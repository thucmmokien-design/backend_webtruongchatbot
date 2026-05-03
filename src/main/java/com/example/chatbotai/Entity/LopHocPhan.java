package com.example.chatbotai.Entity;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "LopHocPhan")
@Data
public class LopHocPhan {

    @Id
    @Column(name = "MaLopHP")
    private String maLopHP;

    @Column(name = "SiSo")
    private Integer siSo;

    @Column(name = "NgayBatDau")
    private LocalDate ngayBatDau;

    @Column(name = "NgayKetThuc")
    private LocalDate  ngayKetThuc;

    @ManyToOne
    @JoinColumn(name = "MaMon")
    private MonHoc monHoc;

    @ManyToOne
    @JoinColumn(name = "MaNhip")
    private NhipHoc nhipHoc;

    @ManyToOne
    @JoinColumn(name = "MaGV")
    private GiangVien giangVien;

    @ManyToOne
    @JoinColumn(name = "MaHocKy")
    private HocKy hocKy;

}
