package com.example.chatbotai.dto.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    
    @JsonProperty(value = "tenNganHang")
    private String tenNganHang;
    
    @JsonProperty(value = "nganHang")
    public void setNganHang(String nganHang) {
        this.tenNganHang = nganHang;
    }
    
    private String soCCCD;
    private String soBaoHiem;
    private String danToc;
    private String tonGiao;
    private String queQuan;
    private String xa;
    private String tinhTP;
    private String quocTich;
    
    @JsonProperty(value = "hoKhauThuongTru")
    private String hoKhauThuongTru;
    
    @JsonProperty(value = "hoKhauTT")
    public void setHoKhauTT(String hoKhauTT) {
        this.hoKhauThuongTru = hoKhauTT;
    }
    
    private String noiO;
}
