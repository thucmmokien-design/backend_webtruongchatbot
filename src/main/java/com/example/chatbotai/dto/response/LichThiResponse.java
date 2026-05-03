package com.example.chatbotai.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LichThiResponse {
    private Integer stt;
    private String maLopHP;
    private String tenMonHoc;
    private Integer soTinChi;
    private LocalDate ngayThi;
    private String gioThi; // Định dạng: "HH:mm - HH:mm"
    private Integer soBuoiDayDu; // SBD - Số báo danh
    private String phongThi;
    private String hinhThuc;
    private String maHocKy;
    private String tenHocKy;
    private String namHoc;
}
