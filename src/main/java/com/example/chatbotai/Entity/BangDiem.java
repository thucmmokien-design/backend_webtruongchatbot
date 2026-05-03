package com.example.chatbotai.Entity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "BangDiem")
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BangDiem {

    @Id
    @Column(name = "ID")
    Integer id;

    @Column(name = "DiemQT")
    Double diemQT;

    @Column(name = "DiemThi")
    Double diemThi;

    @ManyToOne
    @JoinColumn(name = "MaSV")
    SinhVien sinhVien;

    @ManyToOne
    @JoinColumn(name = "MaLopHP")
    LopHocPhan lopHocPhan;
}
