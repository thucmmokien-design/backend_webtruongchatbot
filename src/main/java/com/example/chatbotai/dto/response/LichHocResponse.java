package com.example.chatbotai.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LichHocResponse {
    private String maLichHoc;
    private Integer thu;
    private Integer tietBatDau;
    private Integer tietKetThuc;
    private String phong;
    private LocalDate ngayBatDau;
    private LocalDate ngayKetThuc;
    private String maLopHP;
    private String tenMonHoc;
    private String tenGiangVien;
}
