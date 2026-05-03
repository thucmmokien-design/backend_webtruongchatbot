package com.example.chatbotai.Entity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Table(name = "ThongBao")
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ThongBao {

    @Id
    @Column(name = "MaThongBao")
    String maThongBao;

    @Column(name = "TieuDe")
    String tieuDe;

    @Column(name = "NoiDung")
    String noiDung;

    @Column(name = "NgayDang")
    LocalDateTime ngayDang;

    @Column(name = "PhamVi")
    String phamVi;

    @Column(name = "Tag")
    String tag;

    @Column(name = "Link")
    String link;

    @ManyToOne
    @JoinColumn(name = "MaKhoa")
    Khoa khoa;

    @ManyToOne
    @JoinColumn(name = "MaLop")
    Lop lop;
}
