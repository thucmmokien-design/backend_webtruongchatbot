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
public class XacNhanThanhToanResponse {
    private String maThanhToan;
    private String maSV;
    private String maHocKy;
    private String namHoc;
    private BigDecimal soTien;
    private String phuongThuc;
    private LocalDateTime ngayThanhToan;
    private String trangThaiHocPhi; // Trạng thái mới của học phí
    private String message;
}
