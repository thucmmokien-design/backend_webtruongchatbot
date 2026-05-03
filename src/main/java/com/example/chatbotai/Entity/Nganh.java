package com.example.chatbotai.Entity;

import jakarta.persistence.*;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "Nganh")
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Nganh {

    @Id
    @Column(name = "MaNganh")
    String maNganh;

    @Column(name = "TenNganh")
    String tenNganh;

    @ManyToOne
    @JoinColumn(name = "MaKhoa")
    Khoa khoa;
}
