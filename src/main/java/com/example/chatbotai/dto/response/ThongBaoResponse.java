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
public class ThongBaoResponse {
    private String maThongBao;
    private String tieuDe;
    private String noiDung;
    private LocalDateTime ngayDang;
    private String phamVi;
    private String tag;
    private String link;
    private String tenKhoa;
    private String tenLop;
}
