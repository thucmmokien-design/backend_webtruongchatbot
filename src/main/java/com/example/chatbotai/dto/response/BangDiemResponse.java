package com.example.chatbotai.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BangDiemResponse {
    private Integer stt;
    private String maMon;
    private String tenMon;
    private Integer soTinChi;
    private String maLopHP;
    private String loaiMon;
    private Double diemQT;
    private Double diemThi;
    private Double diemTong;
    private Double diemHe4;
    private Double diemHe10;
    private String xepLoai;
    private String hocKy;
    private String namHoc;
}
