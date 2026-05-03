package com.example.chatbotai.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateThongTinCaNhanRequest {
    private String email;
    private String dienThoai;
    private String soTaiKhoan;
    private String tenNganHang;
    private String soCCCD;
    private String soBaoHiem;
    private String danToc;
    private String tonGiao;
    private String queQuan;
    private String xa;
    private String tinhTP;
    private String quocTich;
    private String hoKhauThuongTru;
    private String noiO;
}
