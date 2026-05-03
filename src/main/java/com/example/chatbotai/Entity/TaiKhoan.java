package com.example.chatbotai.Entity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "TaiKhoan")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaiKhoan {
    @Id
    @Column(name = "Username")
    String username;
    @Column(name = "Password")
    String password;
    @Column(name = "Role")
    String role;
    @ManyToOne
    @JoinColumn(name = "MaSV")
    SinhVien sinhVien;
}
