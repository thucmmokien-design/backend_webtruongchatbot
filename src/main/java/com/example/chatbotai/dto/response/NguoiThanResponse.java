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
public class NguoiThanResponse {
    private Integer id;
    private String hoTen;
    private LocalDate ngaySinh;
    private String dienThoai;
    private String soCCCD;
    private String hoKhauThuongTru;
    private String moiQuanHe;
}
