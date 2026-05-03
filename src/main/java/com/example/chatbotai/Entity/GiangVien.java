package com.example.chatbotai.Entity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "GiangVien")
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GiangVien {

    @Id
    @Column(name = "MaGV")
    String maGV;

    @Column(name = "TenGV")
    String tenGV;

    @Column(name = "Email")
    String email;

    @Column(name = "DienThoai")
    String dienThoai;

    @ManyToOne
    @JoinColumn(name = "MaKhoa")
    Khoa khoa;
}
