package com.example.chatbotai.Entity;

import jakarta.persistence.*;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "Lop")
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Lop {

    @Id
    @Column(name = "MaLop")
    String maLop;

    @Column(name = "TenLop")
    String tenLop;

    @Column(name = "KhoaHoc")
    String khoaHoc;

    @ManyToOne
    @JoinColumn(name = "MaNganh")
    Nganh nganh;
}
