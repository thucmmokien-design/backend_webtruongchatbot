package com.example.chatbotai.Entity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalTime;

@Entity
@Table(name = "TietHoc")
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TietHoc {

    @Id
    @Column(name = "MaTiet")
    Integer maTiet;

    @Column(name = "GioBatDau")
    LocalTime gioBatDau;

    @Column(name = "GioKetThuc")
    LocalTime gioKetThuc;
}
