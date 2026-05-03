package com.example.chatbotai.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HocPhiHocKyResponse {
    private String namHoc;
    private Integer hocKy;
    private BigDecimal mucHocPhi;
    private BigDecimal mienGiam;
    private BigDecimal soTienPhaiNop;
    private String trangThai;
}
