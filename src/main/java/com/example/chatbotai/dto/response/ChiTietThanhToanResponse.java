package com.example.chatbotai.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChiTietThanhToanResponse {
    private String namHoc;
    private Integer hocKy;
    private LocalDateTime ngayNop;
    private String soPhieu;
    private String tenHocPhan;
    private Integer soTinChi;
    private BigDecimal soTienNop;
    private BigDecimal mienGiam;
    private BigDecimal soTienThucNop;
    private String maLopHP;
}
