package com.example.chatbotai.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SinhVienResponse {
    private String maSV;
    private String hoTen;
    private LocalDate ngaySinh;
    private String gioiTinh;
    private String email;
    private String dienThoai;
    private String trangThai;
    private String tenLop;
    private String maLop;
}
