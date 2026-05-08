package com.example.chatbotai.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SinhVienResponse {
    // Thông tin từ SinhVien
    private String maSV;
    private String hoTen;
    private LocalDate ngaySinh;
    private String gioiTinh;
    private String trangThai;
    
    // Thông tin từ Lop
    private String maLop;
    private String tenLop;
    private String khoaHoc;
    private String nienKhoa;
    
    // Thông tin từ Nganh
    private String chuyenNganh;
    private String heDaoTao;
    
    // Thông tin từ ThongTinCaNhan
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
