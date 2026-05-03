package com.example.chatbotai.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HocPhiResponse {
    // Tổng quan
    private BigDecimal tongTienDaNop;
    private BigDecimal tongTienChuaNop;
    
    // Danh sách học phí theo học kỳ
    private List<HocPhiHocKyResponse> danhSachHocPhi;
    
    // Danh sách hóa đơn
    private List<HoaDonResponse> danhSachHoaDon;
    
    // Chi tiết các khoản đã nộp
    private List<ChiTietThanhToanResponse> chiTietDaNop;
}
