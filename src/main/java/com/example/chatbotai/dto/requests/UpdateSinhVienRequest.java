package com.example.chatbotai.dto.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateSinhVienRequest {
    // Thông tin cơ bản từ SinhVien
    private String hoTen;
    private LocalDate ngaySinh;
    private String gioiTinh;
    private String email;
    private String dienThoai;
    
    // Thông tin chi tiết từ ThongTinCaNhan
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
