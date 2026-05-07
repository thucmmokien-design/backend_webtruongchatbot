package com.example.chatbotai.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BieuMauResponse {
    private String maBieuMau;
    private String tenBieuMau;
    private String moTa;
    private String linkDrive;
    private String loaiBieuMau; // Đơn từ / Quy chế / Tài liệu
    private LocalDateTime ngayTao;
}
