package com.example.chatbotai.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class XacNhanThanhToanRequest {
    private String maSV;
    private String maHocKy;
    private String namHoc;
    private BigDecimal soTien;
    private String phuongThuc; // "Chuyển khoản", "Tiền mặt", "VNPay", etc.
    private String maGiaoDich; // Mã giao dịch từ ngân hàng (optional)
}
