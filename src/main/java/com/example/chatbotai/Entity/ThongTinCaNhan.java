package com.example.chatbotai.Entity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
@Entity
@Table(name = "ThongTinCaNhan")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ThongTinCaNhan {
    @Id
    String maSV;

    String email;
    String dienThoai;
    String soTaiKhoan;
    String tenNganHang;
    String soCCCD;
    String soBaoHiem;
    String danToc;
    String tonGiao;
    String queQuan;
    String xa;
    String tinhTP;
    String quocTich;
    String hoKhauThuongTru;
    String noiO;

    @OneToOne
    @JoinColumn(name = "maSV")
    SinhVien sinhVien;
}