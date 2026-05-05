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
public class ThanhToanHocKyResponse {
    private String namHoc;
    private Integer hocKy;
    private String trangThai;
    private BigDecimal tongTienPhaiNop;
    private BigDecimal tongMienGiam;
    private BigDecimal tongThucNop;
    private String qrCodeUrl;
    private ThongTinChuyenKhoan thongTinChuyenKhoan;
    private List<ChiTietMonHocResponse> chiTietMonHoc;
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ThongTinChuyenKhoan {
        private String nganHang;
        private String soTaiKhoan;
        private String tenTaiKhoan;
        private Long soTien;
        private String noiDung;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ChiTietMonHocResponse {
        private String tenHocPhan;
        private Integer soTinChi;
        private BigDecimal soTienNop;
        private BigDecimal mienGiam;
        private BigDecimal soTienThucNop;
        private String maLopHP;
    }
}
