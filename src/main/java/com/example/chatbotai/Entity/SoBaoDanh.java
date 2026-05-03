package com.example.chatbotai.Entity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
@Entity
@Table(
        name = "SoBaoDanh",
        uniqueConstraints = @UniqueConstraint(columnNames = {"MaSV", "MaLopHP"})
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SoBaoDanh {

    @Id
    @Column(name = "SoBaoDanh")
    String soBaoDanh;

    @ManyToOne
    @JoinColumn(name = "MaSV")
    SinhVien sinhVien;

    @ManyToOne
    @JoinColumn(name = "MaLopHP")
    LopHocPhan lopHocPhan;
}
