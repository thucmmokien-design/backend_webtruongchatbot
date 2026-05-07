package com.example.chatbotai.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LopHocDuocMoResponse {
    private String maMo;
    private String maMon;
    private String tenMon;
    private Integer soTinChi;
    private String maGV;
    private String tenGV;
    private String maHocKy;
    private String namHoc;
    private String maNhip;
    private String tenNhip;
    private LocalDate ngayBatDau;
    private LocalDate ngayKetThuc;
    private Integer thu;
    private Integer tietBatDau;
    private Integer tietKetThuc;
    private String phong;
    private Integer siSoToiDa;
    private Integer soLuongTrong;
    private String trangThaiDangKy; // null / Chờ duyệt / Đã duyệt / Từ chối
    private Integer idDangKy; // ID của bản ghi đăng ký (nếu có)
}
